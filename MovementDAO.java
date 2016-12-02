import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;import sql.CoreDAO;
import sql.CoreDAOImpl;
import sql.DAOSysException;
import sql.NoSuchEntityException;


public class MovementDAO extends CoreDAOImpl<MovementModel, MovementPK>{
	
	public MovementDAO() { this(CoreDAO.DRIVER_NAME, CoreDAO.URL, CoreDAO.USER, CoreDAO.PASSWORD);		}
	
	public MovementDAO(String drivername,
			String url,
			String user,
			String password)	{
			super(drivername, url, user, password);
	}
	/* ACCESSORS	-----------------------------------------------	*/

	
	/* MUTATORS	--------------------------------------------------	*/

	
	/* BEHAVIOR	--------------------------------------------------------	*/
	
	
	@Override
	public void dbInsert(MovementModel model) throws DAOSysException {
		dbInsert(model, MovementDAO.INSERT_STM);
		
	}

	@Override
	public void dbInsert(MovementModel model, String insertStm) throws DAOSysException {
		PreparedStatement preparedStm = null;
		Connection connection = null;

		try	{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(insertStm);
			preparedStm.setString(1, model.getNumber());
			preparedStm.setString(2, model.getName());
			
		}	catch (SQLException sex)	{
			throw new DAOSysException("Error adding movement <" + model.getNumber() + "> " + sex.getMessage());

		}	finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}
	}
	

	@Override
	public MovementModel dbSelectByPrimaryKey(MovementPK primarykey) throws DAOSysException, NoSuchEntityException {
		return dbSelectByPrimaryKey(primarykey, MovementDAO.SELECT_STM);
		
	}

	@Override
	public MovementModel dbSelectByPrimaryKey(MovementPK primarykey, String selectStm)
			throws DAOSysException, NoSuchEntityException {
		if (_debug)	System.out.println("CDAO:dbSelectByPrimaryKey(key, stm, model)");
		MovementPK pk = (MovementPK) primarykey;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		boolean result = false;
		MovementModel model = new MovementModel();
		try{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			preparedStm.setString(1, pk.getNumber());
			rs = preparedStm.executeQuery();

			result = rs.next();
			if (result){
				model.setPrimarykey(new MovementPK(rs.getString(1)));
				model.setName(rs.getString(2));
			} else{
				throw new NoSuchEntityException("Movement ID for <" + primarykey+ "> not found in the database.");
			}
			
		}catch (SQLException sex){
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

	@Override
	public Collection<MovementPK> dbSelectAll() throws DAOSysException {
		// TODO Auto-generated method stub
		return dbSelectAll(MovementDAO.SELECT_ALL_STM);
	}

	@Override
	public Collection<MovementPK> dbSelectAll(String selectStm) throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		ArrayList<MovementPK> list = null;
		
		try{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			rs = preparedStm.executeQuery();
			list = new ArrayList<MovementPK>();
			while(rs.next()){
				list.add(new MovementPK(rs.getString(1)));
			}
		} catch(SQLException sex){
			 new DAOSysException(
						"dbSelectAll() SQL Exception\n"
						+ sex.getMessage());
		} finally{
			try{
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex){
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}
		
		return list;
	}

	@Override
	public void dbUpdate(MovementModel data) throws DAOSysException {
		dbUpdate(data, MovementDAO.UPDATE_STM);
		
	}

	@Override
	public void dbUpdate(MovementModel data, String updateStm) throws DAOSysException {
		MovementModel model = data;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		try{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(updateStm);
			preparedStm.setString(1, model.getName());
			preparedStm.setString(2, model.getNumber());
			
			int rowCount = preparedStm.executeUpdate();
			if (rowCount == 0){
				throw new DAOSysException(
	 					"Failed to store state for Customer <"
	 					+ model.getNumber() + ">");
	 		}
		} catch (SQLException sex){
			throw new DAOSysException(
					"dbUpdate() SQL Exception <"
					+ sex.getMessage() + ">");
		}finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (Exception ex)	{
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}
		
	}

	@Override
	public int dbRemove(MovementPK primarykey) throws DAOSysException {
		return dbRemove(primarykey, MovementDAO.DELETE_STM);
	}

	@Override
	public int dbRemove(MovementPK primarykey, String deleteStm) throws DAOSysException {
		MovementPK pk = primarykey;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		int result = 0;
		
		try{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(deleteStm);
			preparedStm.setString(1, pk.getNumber());
			result = preparedStm.executeUpdate();

			if (result == 0)	{
				throw new SQLException(
						"Failed to remove Movement <"
						+ pk.toString() + ">.");
			}
		} catch (SQLException sex){
			throw new DAOSysException(
					"dbRemove() SQL Exception <" + pk.toString() + "> " + sex.getMessage());
		}finally	{
			try	{
				releaseAll(null, preparedStm, connection);
			} catch (SQLException sqlex)	{
				throw new DAOSysException(sqlex.toString());
			}
		}
		return result;
	}

	@Override
	public int dbCountTotalEntities() throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		int count = 0;
		try{
			connection = connectToDB();
			preparedStm = connection.prepareStatement(MovementDAO.SELECT_DISTINCT_STM,
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_UPDATABLE);
			rs = preparedStm.executeQuery();
			rs.last();
			count = rs.getRow();
		} catch (SQLException sex){
			throw new DAOSysException(
					"dbCountTotalMovements() SQL Exception\n"
					+ sex.getMessage());
		}finally	{
			try	{
				releaseAll(rs, preparedStm, connection);
			} catch	(SQLException sqlex)	{
				throw new DAOSysException(sqlex.toString());
			}
		}
		
		return count;
	}
	
	
	
	
	/* ATTRIBUTES	-----------------------------------------------	*/
	private final static boolean _debug = false;

	private static String SELECT_DISTINCT_STM =
		"SELECT DISTINCT number FROM " + "Movement";
	
	private static String DELETE_STM =
		"DELETE FROM " + "Movement"
		+ " WHERE number = ?";
	
	private static String UPDATE_STM =
		"UPDATE " + "Movement"
		+ " SET "
		+ "name = ? "
		+ "where number = ?";

	private static String SELECT_ALL_STM =
		"SELECT DISTINCT number " + "FROM " + "Movement";
	
	
	
	private static String SELECT_STM = "SELECT "
		+ " number "
		+ " FROM MOVEMENT "
		+ " WHERE number = ?";
		
	private static String INSERT_STM = "INSERT INTO "
		+ "Movement"
		+ " VALUES "
		+ "( ? )";
	
} /*	End of Class:	MovementDAO.java				*/
