/*
 * CompositionDAO.java
 */

package marina;

import java.util.*;
import java.sql.*;

import sql.CoreDAOImpl;
import sql.CoreDAO;
import sql.DAOSysException;
import sql.NoSuchEntityException;

/**
 *	Data access object for conductor data.  This class bridges the
 *	object to non-object datastore layer.
 * @author Reg
 */
public class CompositionDAO extends CoreDAOImpl<CompositionModel, CompositionPK>	{
	/**
	 * Creates a new instance of CompositionDAO
	 */
	public CompositionDAO() { this(CoreDAO.DRIVER_NAME, CoreDAO.URL, CoreDAO.USER, CoreDAO.PASSWORD);		}

	/**
	 *	Parameterized constructor.  When extending this class the
	 *	derived class must invoke one of this classes constructors
	 *	for proper initialization.
	 *	@param drivername 
	 * @param url 
	 * @param	user		Database user name.
	 *	@param	password	Database password for access.
	 */
	public CompositionDAO(String drivername,
			String url,
			String user,
			String password)	{
		super(drivername, url, user, password);
	}


	/* ACCESSORS	-----------------------------------------------	*/


	/* MUTATORS	--------------------------------------------------	*/


	/* BEHAVIOR	--------------------------------------------------------	*/
	/**
	 * Called by create() to insert entity state into the data store for a new entity.
	 *	@param	model	Persistence data model for the entity.
	 *	@throws	DAOSysException
	 */
	public void dbInsert(CompositionModel model)	throws DAOSysException {
		dbInsert(model, CompositionDAO.INSERT_STM);
	}


	/**
	 * Called by create() to insert entity state into the data store for a new entity.
	 *	@param	model	Persistence data model for the entity.
	 *	@param	insertStm	Data store statement for inserting into the data store.
	 *	@throws	DAOSysException
	 */
	public void dbInsert(CompositionModel model, String insertStm) throws DAOSysException		{
		PreparedStatement preparedStm = null;
		Connection connection = null;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(insertStm);
			preparedStm.setString(1, model.getcomposer());
			preparedStm.setString(2, model.getcompositionName());
			preparedStm.executeUpdate();

		}	catch (SQLException sex)	{
			throw new DAOSysException("Error adding conductor <" + model.getcomposer() + "> " + sex.getMessage());

		}	finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}
	}

	/**
	 * Called by findByPrimaryKey() to retrieve an entity by the primary key.
	 *	@param	primarykey The primary key for an entity.
	 *	@return	The persistence model for the entity, else null entity data is not found.
	 * @throws sql.DAOSysException
	 * @throws sql.NoSuchEntityException 
	 */
	public CompositionModel dbSelectByPrimaryKey(CompositionPK primarykey)
			throws DAOSysException, NoSuchEntityException	{
		return dbSelectByPrimaryKey(primarykey, CompositionDAO.SELECT_STM);
	}

	/**
	 * Called by findByPrimaryKey() to retrieve an entity by the primary key.
	 *	@param	primarykey The primary key for an entity.
	 *	@param	selectStm	Data store statement to get the entity.
	 *	@return	The persistence model for the entity, else null entity data is not found.
	 */
	public CompositionModel dbSelectByPrimaryKey(CompositionPK primarykey, String selectStm)
			throws DAOSysException, NoSuchEntityException	{
		if (_debug)	System.out.println("CDAO:dbSelectByPrimaryKey(key, stm, model)");
		CompositionPK pk = (CompositionPK) primarykey;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		boolean result = false;
		CompositionModel model = new CompositionModel();
		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			preparedStm.setString(1, pk.getcomposer());
			rs = preparedStm.executeQuery();

			result = rs.next();
			if (result)	{
				model.setPrimarykey(new CompositionPK(rs.getString(1)));
				model.setcompositionName(rs.getString(2));


			}	else	{
				throw new NoSuchEntityException("conductor composer for <"
						+ primarykey + "> not found in the database.");
			}

		}	catch (SQLException sex)	{
			throw new DAOSysException(
					"dbSelectByPrimaryKey() SQL Exception\n"
							+ sex.getMessage());

		}	finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}

		return model;

	}

	/**
	 * Called by findAll() to find all entities in the data store.
	 *	@return	A collection of primary keys representing all of the entities.
	 */
	public Collection<CompositionPK> dbSelectAll()	throws DAOSysException {
		return dbSelectAll(CompositionDAO.SELECT_ALL_STM);
	}


	/**
	 * Called by findAll() to find all entities in the data store.
	 *	@param selectStm 
	 * @return	A collection of primary keys representing all of the entities.
	 */
	public Collection<CompositionPK> dbSelectAll(String selectStm)	throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		ArrayList<CompositionPK> list = null;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			rs = preparedStm.executeQuery();

			list = new ArrayList<CompositionPK>();
			while (rs.next())	{
				list.add(new CompositionPK(rs.getString(1)));
			}

		}	catch (SQLException sex)	{
			throw new DAOSysException(
					"dbSelectAll() SQL Exception\n"
							+ sex.getMessage());
		}	finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}

		return list;
	}
	//adding a selection of primary keys which we now iterate through and build conductor objects by executing a find by primary key . We have array of primary keys and by the time
	//were done we have a array of conductor objects
	//we may have to create new finder methods that match the way we need to search composer composition and movements.. Take the same approach as 

	/**
	 * Called by update() to update state for an entity in the database.
	 *	@param	data	Persistence model for the entity.
	 *	@throws	DAOSysException
	 */
	public void dbUpdate(CompositionModel data)	throws DAOSysException	{
		dbUpdate(data, CompositionDAO.UPDATE_STM);
	}

	/**
	 * Called by update() to update state for an entity in the database.
	 *	@param	data	Persistence model for the entity.
	 *	@param	updateStm	Data store update statement.
	 *	@throws	DAOSysException
	 */
	public void dbUpdate(CompositionModel data, String updateStm)	throws DAOSysException {
		CompositionModel model = data;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(updateStm);

			/*	Grab values from persistent fields to store in database	*/
			preparedStm.setString(1, model.getcompositionName());
			preparedStm.setString(4, model.getcomposer());

			int rowCount = preparedStm.executeUpdate();
			if (rowCount == 0)	{
				throw new DAOSysException(
						"Failed to store state for conductor <"
								+ model.getcomposer() + ">");
			}

		}	catch (SQLException sex)	{
			throw new DAOSysException(
					"dbUpdate() SQL Exception <"
							+ sex.getMessage() + ">");

		}	finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}
	}

	/**
	 * Called by remove() to remove the state for an entity from the data store.
	 *	@param	primarykey	The primary key of the entity to be removed.
	 *	@return 
	 * @throws	DAOSysException
	 */
	public int dbRemove(CompositionPK primarykey)	throws DAOSysException	{
		return dbRemove(primarykey, CompositionDAO.DELETE_STM);
	}


	/**
	 * Called by remove() to remove the state for a conductor entity from the database.
	 *	@param	primarykey	The primary key of the conductor entity
	 *	to be removed from the data store.
	 *	@param	deleteStm	Statement to remove entity data from the data store.
	 *	@throws	DAOSysException
	 */
	public int dbRemove(CompositionPK primarykey, String deleteStm)	throws DAOSysException	{
		CompositionPK pk = primarykey;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		int result = 0;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(deleteStm);
			preparedStm.setString(1, pk.getcomposer());
			result = preparedStm.executeUpdate();

			if (result == 0)	{
				throw new SQLException(
						"Failed to remove conductor <"
								+ pk.toString() + ">.");
			}

		}	catch (SQLException sex)	{
			throw new DAOSysException(
					"dbRemove() SQL Exception <" + pk.toString() + "> " + sex.getMessage());

		}	finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (SQLException sqlex)	{
				throw new DAOSysException(sqlex.toString());
			}
		}

		return result;
	}


	/**
	 * Called by getTotalEntities().
	 *	@return	The total number of entities in the data store.
	 *	@throws	DAOSysException
	 */
	public int dbCountTotalEntities()	throws DAOSysException	{
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		int count = 0;

		try	{
			connection = connectToDB();
			/*	Request a resultset that is scrollable to easily count rows	*/
			preparedStm = connection.prepareStatement(
					CompositionDAO.SELECT_DISTINCT_STM,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = preparedStm.executeQuery();

			/*	Go to the last row and get its row number							*/
			rs.last();
			count = rs.getRow();

		}	catch (SQLException sex)	{
			throw new DAOSysException(
					"dbCountTotalconductors() SQL Exception\n"
							+ sex.getMessage());

		}	finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch	(SQLException sqlex)	{
				throw new DAOSysException(sqlex.toString());
			}
		}

		return count;
	}

	//dont need update or delete for project2

	/* ATTRIBUTES	-----------------------------------------------	*/
	private final static boolean _debug = false;

	private static String SELECT_DISTINCT_STM =
			"SELECT DISTINCT composer FROM " + "composition";

	private static String DELETE_STM =
			"DELETE FROM " + "composition"
					+ " WHERE composer = ?";

	private static String UPDATE_STM =
			"UPDATE " + "composition"
					+ " SET "
					+ "compositionName = ? "
					+ "where composer = ?";

	private static String SELECT_ALL_STM =
			"SELECT DISTINCT composer " + "FROM " + "composition";



	private static String SELECT_STM = "SELECT "
			+ " composer, "
			+ " compositionName, "
			+ " FROM composition "
			+ " WHERE composer = ?";

	private static String INSERT_STM = "INSERT INTO "
			+ "composition"
			+ " VALUES "
			+ "( ?, ? )";


}	/*	End of Class:	CompositionDAO.java				*/
