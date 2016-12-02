/**
 * Movement PK is the primary key class for a Movement entity
 * @author 
 *
 */
public class MovementPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public MovementPK(){}
	
	/**
	 *	Constructor to build a primary key from an Number.
	 *	@param	number	The Movement number.
	 */
	public MovementPK(String n){ this.number = n; }
	/**
	 *	Constructor to build a primary key from a another MovementPK argument.
	 *	@param	primarykey	A MovementPK object.
	 */
	public MovementPK(MovementPK pk){ };
	
	/* ACCESSORS	--------------------------------------------------	*/
	/**
	 *	Get the Movement Number.
	 *	@return	The Movement number.
	 */
	public String getNumber(){return this.getNumber(); }
	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Convert this primary key object into a meaningful string.
	 *	@return	This object as a string.
	 */
	public String toString() {return this.getNumber();}
	
	public boolean equals(Object obj)	{
		return	obj instanceof MovementPK
			&&	getNumber().equals(((MovementPK) obj).getNumber()
					);
	}
	
	public int hashCode(){
		return getNumber().hashCode();
	}
	
	
	
	
	
	/*	Movement Entity PRIMARY KEY FIELDS ------------------------------	*/
	/** Movement number.																	*/
	private String number;

}	/*	End of Class:	MovementPK.java				*/

