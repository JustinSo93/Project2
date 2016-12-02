package marina;

import sql.*;

import java.util.*;

import sql.CreateException;
import sql.FinderException;
import sql.NoSuchEntityException;

/**
 * Composition class
 * Demonstrates the use of the provided DAO framework.
 * 	includes basic Composition attributes plus
 *			Boat reference attribute and accessors
 *			and Lease reference attribute and accessors
 */
public class Composition{
	/* STATIC PRE-OBJECT BEHAVIOR	-----------------------------------	*/
	/*	Basic Creators, finders, and removers									*/
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a new Composition.
	 *	@return	An instance of a Composition entity.
	 *	@throws sql.CreateException 
	 * @param	composer	The Composition composer.
	 *	@param	compositionName		The compositionName of the Composition. 
	 *	@param	address	The address for this Composition.
	 *	@param	phoneno	The phone composer for this Composition.
	 */
	public static Composition create(String composer,
			String compositionName,
			String address,
			String phoneno)
					throws CreateException				{
		if (_debug) System.out.println("C:create:" + composer);

		CompositionModel model = new CompositionModel(composer, compositionName);
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(classcompositionName);
			dao.dbInsert(model);

			/* Initially this Composition has no boats or leases			*/

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}

		return	new Composition(model);
	}

	/* FINDERS	-----------------------------------------------------	*/
	/*	Finder methods are used to search for a particular instance
	 *	or a collection of instances, therefore finders either return
	 *	and instance to the entity, or a collection of instances.
	 */
	/**
	 *	Find a Composition by primary key.
	 *	@return	An instance of a Composition entity.
	 *	@throws sql.FinderException 
	 * @throws sql.NoSuchEntityException 
	 * @param	primarykey	The primary key for the Composition to find.
	 */
	public static Composition findByPrimarykey(CompositionPK primarykey)
			throws FinderException, NoSuchEntityException			{
		if (_debug) System.out.println("C:findByPrimarykey(" + primarykey + ")");

		CompositionModel model = null;
		Composition Composition = null;
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(classcompositionName);
			model = (CompositionModel) dao.dbSelectByPrimaryKey(primarykey);
			Composition = new Composition(model);

			//				/* Add boat references for this Composition.				*/
			//				Composition.setBoats( ((ArrayList<Boat>) Boat.findByComposition(Composition)) );

			/*	Add lease references for this Composition.			*/



		} catch (Exception sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return Composition;
	}

	/**
	 *	Find all Composition entities.
	 *	@return	A collection of Composition instances.
	 *	@throws	FinderException
	 * @throws	CreateException
	 */
	public static Collection<Composition> findAll() throws FinderException, CreateException			{
		ArrayList<Composition> listOfCompositions = new ArrayList<Composition>();
		CompositionDAO dao = null;

		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(classcompositionName);
			Collection<CompositionPK> c = dao.dbSelectAll();
			Iterator<CompositionPK> itr = c.iterator();
			while (itr.hasNext())	{
				CompositionPK cpk = itr.next();
				try	{
					Composition composition = Composition.findByPrimarykey(cpk);
					//						/* Add boat references for this Composition.				*/
					//						Composition.setBoats(((ArrayList<Boat>) Boat.findByComposition(Composition)));

					//						/* Add leases for this Composition							*/


					/* Add this Composition to the list.						*/
					listOfCompositions.add(composition);

				} catch (Exception ex)	{
					System.err.println("Composition: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}


		return listOfCompositions;
	}


	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove a Composition by primary key.
	 *	@param	primarykey	The primary key for the Composition to find.
	 *	@throws	NoSuchEntiryException
	 * @throws	DAOSysException
	 */
	private static int removeByPrimarykey(CompositionPK primarykey)
			throws	DAOSysException, NoSuchEntityException	{
		int rc = 0;
		CompositionDAO dao = null;

		/*	remove boats first etc...				*/

		dao = (CompositionDAO) DAOFactory.getDAO(classcompositionName);
		rc = dao.dbRemove(primarykey);

		return rc;
	}


	/* CONSTRUCTORS	-----------------------------------------------	*/
	/**
	 *	Default constructor
	 */
	private Composition()	{ super();		}

	/**
	 *	Parameterized constructor.
	 *	@param	compositionName
	 *	@param	address
	 *	@param	phoneno
	 */
	private Composition(String composer, String compositionName)	{
		this(new CompositionModel(composer, compositionName));
	}

	/**
	 *	Parameterized constructor.
	 *	@param	model	The persistence model for a Composition object.
	 */
	private Composition(CompositionModel model)	{
		setModel(model);

		/*	initially no Boat and no leases, but we do have empty collections		*/
		setListOfBoats(new ArrayList<Boat>());
		//			setListOfLeases(new ArrayList<Lease>());
	}


	/* ACCESSORS	--------------------------------------------------	*/
	public CompositionModel getModel()				{ return model;												}
	public CompositionPK getPrimaryKey()			{ return getModel().getPrimarykey();					}
	public String getcomposer()						{ return getModel().getPrimarykey().getcomposer(); 	}
	public String getcompositionName()				{ return getModel().getcompositionName();							}
	public ArrayList<Boat>	getListOfBoats()	{ return listOfBoats;										}
	//		public ArrayList<Lease>	getListOfLeases()	{ return listOfLeases;										}

	/**	private ArrayList<Boat> getBoats()	{
			ArrayList<Boat> list = new ArrayList<Boat>();
			try	{
				list = (ArrayList<Boat>) Boat.findByComposition(this);
			} catch (Exception ex)	{
			}
			return list;
		} **/

	//		private ArrayList<Lease> getLeases(){
	//			ArrayList<Lease> list = new ArrayList<Lease>();
	////			list = Lease.findByComposition(this);
	//			return list;
	//		}


	/* MODIFIERS	--------------------------------------------------	*/
	private void setModel(CompositionModel model)	{ this.model = model;								}

	private void setPrimarykey(CompositionPK pk)	{ getModel().setPrimarykey(pk);						}
	public void setcompositionName(String compositionName)				{
		getModel().setcompositionName(compositionName);
		update();
	}


	private void setListOfBoats(ArrayList<Boat> boats)		{ listOfBoats = boats;						}
	//		private void setListOfLeases(ArrayList<Lease> leases)	{ listOfLeases = leases;					}


	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Implementation of the "object" equals method.  Compositions objects are equal
	 *	if their primary key's are equal.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	public boolean equals(Object obj)	{
		return	obj instanceof Composition
				&&	(getcomposer().equals(((Composition) obj).getcomposer())
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
		return	getcomposer().concat(
				getcompositionName()
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

	/**	
		public String toString()	{ return this.toString(", ");				}
		public String toString(String sep)	{
			return "composer=" + getcomposer()
					+ sep + "compositionName=" + getcompositionName()
					+ sep + "listOfBoats=" + getListOfBoats()
//					+ sep + "listOfLeases=" + getListOfLeases()
					+ sep + "boats=" + getBoats()
//					+ sep + "leases=" + getLeases()
				;
		}

		/**
	 *	Get an iterator to the list of boats for this Composition.
	 * 
	 * @return 
	 */
	//public Iterator<Boat> boats()					{ return getBoats().iterator();								}

	//		/**
	//		 *	Get an iterator to the list of leases for this Composition.
	//		 * 
	//		 * @return 
	//		 */
	//		public Iterator leases()				{ return getLeases().iterator();								}

	/**
	 *	Add a boat to this Composition.
	 * 
	 * @param boat 
	 */
	public void addBoat(Boat boat)	{
		if (!getListOfBoats().contains(boat))	{
			getListOfBoats().add(boat);
		}
	}

	/**
	 *	Remove a boat from this Composition
	 * 
	 * @param boat 
	 */
	//		public void removeBoat(Boat boat)	{
	//	getBoats().remove(boat);
	//		}

	//		/**
	//		 *	Add a lease to this Composition
	//		 * 
	//		 * @param lease 
	//		 */
	//		public void addLease(Lease lease)	{
	//			if (!getLeases().contains(lease))	{
	//				getLeases().add(lease);
	//			}
	//		}

	//		/**
	//		 *	Remove a lease from this Composition
	//		 * 
	//		 * @param lease 
	//		 */
	//		public void removeLease(Lease lease)	{
	//			getLeases().remove(lease);
	//		} 



	/**
	 *	Remove a Composition from the data store (by primary key).
	 * @return 
	 * @throws sql.NoSuchEntityException
	 * @throws sql.DAOSysException 
	 */
	public Composition remove()	throws NoSuchEntityException, DAOSysException	{
		Composition c = null;
		if (removeByPrimarykey(getPrimaryKey()) > 0)	{
			c = this;
		}

		return c;
	}

	/**
	 * Invoke this method to refresh the cached attribute values
	 * from the database.
	 */
	private void load() throws DAOSysException		{
		if (_debug) System.out.println("C:load()");
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(classcompositionName);
			setModel((CompositionModel)dao.dbLoad(getPrimaryKey()));

		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}


	/**
	 * Invoke this method to save the cached attribute values to the datastore.
	 */
	private void store()	throws DAOSysException		{
		if (_debug) System.out.println("C:store()");
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(classcompositionName);
			dao.dbStore(getModel());
		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}



	/* ATTRIBUTES	--------------------------------------------------	*/
	private static final boolean _debug = false;

	/** Class compositionName for static method purposes.								*/
	private static String classcompositionName = "marina.Composition";

	/** Persistence model for a Composition object.								*/
	private CompositionModel model;


	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/
	/** Boat for this Composition.													*/
	private ArrayList<Boat> listOfBoats;

	//		/** Lease for this Composition.													*/
	//		private ArrayList<Lease> listOfLeases;


}	/*	End of CLASS:	Composition.java				*/

