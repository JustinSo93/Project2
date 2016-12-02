
package marina;

import sql.*;

import java.util.*;

/**
 * Composer class
 * Demonstrates the use of the provided DAO framework.
 * 	includes basic Composer attributes plus
 *			Boat reference attribute and accessors
 *			and Lease reference attribute and accessors
 */
public class Composer		{
	/* STATIC PRE-OBJECT BEHAVIOR	-----------------------------------	*/
	/*	Basic Creators, finders, and removers									*/
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a new Composer.
	 *	@return	An instance of a Composer entity.
	 *	@throws sql.CreateException 
	 * @param	number	The Composer number.
	 *	@param	name		The name of the Composer. 
	 *	@param	address	The address for this Composer.
	 *	@param	phoneno	The phone number for this Composer.
	 */
	public static Composer create(String id,
											String name)
								throws CreateException				{
		if (_debug) System.out.println("C:create:" + id);

		ComposerModel model = new ComposerModel(id, name);
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);
			
			/* Initially this Composer has no boats or leases			*/

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}

		return	new Composer(model);
	}
	
	/* FINDERS	-----------------------------------------------------	*/
	/*	Finder methods are used to search for a particular instance
	 *	or a collection of instances, therefore finders either return
	 *	and instance to the entity, or a collection of instances.
	 */
	/**
	 *	Find a Composer by primary key.
	 *	@return	An instance of a Composer entity.
	 *	@throws sql.FinderException 
	 * @throws sql.NoSuchEntityException 
	 * @param	primarykey	The primary key for the Composer to find.
	 */
	public static Composer findByPrimarykey(ComposerPK primarykey)
								throws FinderException, NoSuchEntityException			{
		if (_debug) System.out.println("C:findByPrimarykey(" + primarykey + ")");

		ComposerModel model = null;
		Composer Composer = null;
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			model = (ComposerModel) dao.dbSelectByPrimaryKey(primarykey);
			Composer = new Composer(model);

//			/* Add boat references for this Composer.				*/
//			Composer.setBoats( ((ArrayList<Boat>) Boat.findByComposer(Composer)) );

			/*	Add lease references for this Composer.			*/

			
			
		} catch (Exception sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return Composer;
	}

	/**
	 *	Find all Composer entities.
	 *	@return	A collection of Composer instances.
	 *	@throws	FinderException
	 * @throws	CreateException
	 */
	public static Collection<Composer> findAll() throws FinderException, CreateException			{
		ArrayList<Composer> listOfComposers = new ArrayList<Composer>();
		ComposerDAO dao = null;
	
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			Collection<ComposerPK> c = dao.dbSelectAll();
			Iterator<ComposerPK> itr = c.iterator();
			while (itr.hasNext())	{
				ComposerPK cpk = itr.next();
				try	{
					Composer composer = Composer.findByPrimarykey(cpk);
//					/* Add boat references for this Composer.				*/
//					Composer.setBoats(((ArrayList<Boat>) Boat.findByComposer(Composer)));

//					/* Add leases for this Composer							*/
					
					
					/* Add this Composer to the list.						*/
					listOfComposers.add(composer);

				} catch (Exception ex)	{
					System.err.println("Composer: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}

		
		return listOfComposers;
	}
	
	
	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove a Composer by primary key.
	 *	@param	primarykey	The primary key for the Composer to find.
	 *	@throws	NoSuchEntiryException
	 * @throws	DAOSysException
	 */
	private static int removeByPrimarykey(ComposerPK primarykey)
								throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		ComposerDAO dao = null;
		
		/*	remove boats first etc...				*/
		
		dao = (ComposerDAO) DAOFactory.getDAO(className);
		rc = dao.dbRemove(primarykey);
		
		return rc;
	}

	
	/* CONSTRUCTORS	-----------------------------------------------	*/
	/**
	 *	Default constructor
	 */
	private Composer()	{ super();		}

	/**
	 *	Parameterized constructor.
	 *	@param	name
	 *	@param	address
	 *	@param	phoneno
	 */
	private Composer(String id, String name)	{
		this(new ComposerModel(id, name));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Composer object.
	 */
	private Composer(ComposerModel model)	{
		setModel(model);

		/*	initially no Boat and no leases, but we do have empty collections		*/
		
//		setListOfLeases(new ArrayList<Lease>());
	}


	/* ACCESSORS	--------------------------------------------------	*/
	public ComposerModel getModel()				{ return model;												}
	public ComposerPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
	public String getid()						{ return getModel().getPrimarykey().getid(); 	}
 	public String getName()							{ return getModel().getName();							}
						


	
	/* MODIFIERS	--------------------------------------------------	*/
	private void setModel(ComposerModel model)	{ this.model = model;								}

	private void setPrimarykey(ComposerPK pk)	{ getModel().setPrimarykey(pk);						}
	public void setName(String name)				{
		getModel().setName(name);
		update();
	}
	

	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Implementation of the "object" equals method.  Composers objects are equal
	 *	if their primary key's are equal.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	public boolean equals(Object obj)	{
		return	obj instanceof Composer
			&&	(getid().equals(((Composer) obj).getid())
			);
	}

	/**
	 *	Implementation of the "object"hashCode()" method.
	 * Whenever it is invoked on the same object more than once during
	 * an execution of a Java application, the hashCode method
	 * must consistently return the same integer, provided no information
	 * used in equals comparisons on the object is modified.
	 *	@return	A hash code value for the object.
	 */
	public int hashCode() {
		return	getid().concat(
								getName()
				
							).hashCode();
	}

	/**
	 *	Flush cached attribute values to the datastore.
	 *	Catch and report any errors.
	 */
	public void update()	{
		if (_debug) System.out.println("C:update()");
		try	{
			store();
		} catch (Exception ex)	{
			System.out.println("C: Error in update(), <" + ex.toString() + ">");
		}
	}

	
	public String toString()	{ return this.toString(", ");				}
	public String toString(String sep)	{
		return "id =" + getid()
				+ sep + "name=" + getName();
				
				
	}
						


	/**
	 *	Remove a Composer from the data store (by primary key).
	 * @return 
	 * @throws sql.NoSuchEntityException
	 * @throws sql.DAOSysException 
	 */
	public Composer remove()	throws NoSuchEntityException, DAOSysException	{
		Composer c = null;
		if (removeByPrimarykey(getPrimaryKey()) > 0)	{
			c = this;
		}

		return c;
	}
	private void load() throws DAOSysException		{
		if (_debug) System.out.println("C:load()");
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			setModel((ComposerModel)dao.dbLoad(getPrimaryKey()));

		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}


	/**
	 * Invoke this method to save the cached attribute values to the datastore.
	 */
	private void store()	throws DAOSysException		{
		if (_debug) System.out.println("C:store()");
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());
		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}


private static final boolean _debug = false;
private static String className = "marina.Composer";
private ComposerModel model;
	}

	/*	End of CLASS:	Composer.java				*/