import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import sql.*;
public class Movement {
	
	public static Movement create(String number, String name) throws CreateException{
		if (_debug) System.out.println("C:create:" + number);
		MovementModel model = new MovementModel(number, name);
		MovementDAO dao = null;
		
		try{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);
		}catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}
		return new Movement(model);
	}
	/* FINDERS	-----------------------------------------------------	*/
	
	public static Movement findByPrimaryKey(MovementPK primarykey)
			throws FinderException, NoSuchEntityException{
		if (_debug) System.out.println("C:findByPrimarykey(" + primarykey + ")");
		
		MovementModel model = null;
		Movement movement = null;
		MovementDAO dao = null;
		
		try{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			model = (MovementModel) dao.dbSelectByPrimaryKey(primarykey);
			movement = new Movement(model);
			
		}catch (Exception sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}
		return movement;
	}
	
	public static Collection<Movement> findAll() throws FinderException, CreateException			{
		ArrayList<Movement> listofMovements = new ArrayList<Movement>();
		MovementDAO dao = null;
		
		try{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			Collection<MovementPK> c = dao.dbSelectAll();
			Iterator<MovementPK> itr = c.iterator();
			while (itr.hasNext()){
				MovementPK mpk = itr.next();
				try{
					Movement movement = Movement.findByPrimaryKey(mpk);
					listofMovements.add(movement);
				}catch (Exception ex)	{
					System.err.println("Customer: Error processing list <" + ex.toString());
				}
			}
		}catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}
		return listofMovements;
		
		
	}
	/* REMOVERS	-----------------------------------------------------	*/
	private static int removeByPrimaryKey(MovementPK primarykey) throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		MovementDAO dao = null;
		dao = (MovementDAO) DAOFactory.getDAO(className);
		rc = dao.dbRemove(primarykey);
		
		return rc;
	}
	
	
	
	/* CONSTRUCTORS	-----------------------------------------------	*/
	private Movement()	{ super();		}
	
	private Movement(String number, String name){
		this (new MovementModel(number, name));
	}
	
	private Movement(MovementModel model){
		setModel(model);
	}
	
	/* ACCESSORS	--------------------------------------------------	*/
	public MovementModel getModel()				{ return model;}
	public MovementPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
 	public String getName()							{ return getModel().getName();							}
 	public String getNumber()						{ return getModel().getPrimarykey().getNumber(); 	}
	/* MODIFIERS	--------------------------------------------------	*/
	private void setModel(MovementModel model)	{ this.model = model;								}
	private void setPrimarykey(MovementPK pk)	{ getModel().setPrimarykey(pk);						}
	public void setName(String name)				{
		getModel().setName(name);
		update();
	}
	
	/* BEHAVIOR	-----------------------------------------------------	*/
	public boolean equals(Object obj){
		return obj instanceof Movement && (getNumber().equals(((Movement) obj).getNumber())
				);
	}
	public int hashCode(){
		return getNumber().concat(getName()).hashCode();
	}
	public void update()	{
		if (_debug) System.out.println("C:update()");
		try	{
			store();
		} catch (Exception ex)	{
			System.out.println("C: Error in update(), <" + ex.toString() + ">");
		}
	}
	
	public String toString(){ return this.toString(",");}
	
	public String toString (String sep){
		return "number=" +getNumber() + sep + "name" + getName();
	}
	
	public Movement remove() throws NoSuchEntityException, DAOSysException	{
		Movement m = null;
		if (removeByPrimaryKey(getPrimaryKey()) > 0)	{
			m = this;
		}
		return m;
	}
	
	private void load() throws DAOSysException{
		if (_debug) System.out.println("C:load()");
		MovementDAO dao = null;
		try{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			setModel((MovementModel)dao.dbLoad(getPrimaryKey()));
		}catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}
	
	private void store()	throws DAOSysException		{
		if (_debug) System.out.println("C:store()");
		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());
		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}
	
	
	/* ATTRIBUTES	--------------------------------------------------	*/
	private static final boolean _debug = false;
	private static String className = "Symphony_Project_2.Movement";
	private MovementModel model;
}
