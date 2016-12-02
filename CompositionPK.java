/*
 *  @(#)CompositionPK.java
 */

package	marina;

/**
 * CompositionPK is the primary key class for a Customer entity.
 * @author    R. Dyer
 * @version   1.0.0 May 2002
 */
public class CompositionPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public CompositionPK()	{}

	/**
	 *	Constructor to build a primary key from an Number.
	 *	@param	number	The customer number.
	 */
	public CompositionPK(String composer)	{ this.composer = composer;		}

	/**
	 *	Constructor to build a primary key from a another CompositionPK argument.
	 *	@param	primarykey	A CompositionPK object.
	 */
	public CompositionPK(CompositionPK primarykey)	{ composer = primarykey.getcomposer();		}


	/* ACCESSORS	--------------------------------------------------	*/
	/**
	 *	Get the customer Number.
	 *	@return	The customer number.
	 */
	public String getcomposer()	{ return composer;		}


	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Convert this primary key object into a meaningful string.
	 *	@return	This object as a string.
	 */
	public String toString()	{ return	composer;		}


	/**
	 *	Implemenation of the "object" equals method.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	public boolean equals(Object obj)	{
		return	obj instanceof CompositionPK
			&&	getcomposer().equals(((CompositionPK) obj).getcomposer()
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
		return	getcomposer().hashCode();
	}



	/*	Customer Entity PRIMARY KEY FIELDS ------------------------------	*/
	/** Customer number.																	*/
	private String composer;

}	/*	End of Class:	CompositionPK.java				*/