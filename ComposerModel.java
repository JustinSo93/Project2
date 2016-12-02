/*
 * ComposerModel.java
 */

package marina;

import sql.CorePersistenceModel;

/**
 * ComposerModel represents the persistence model for a Composer object.
 * @author Reg
 */
public class ComposerModel extends CorePersistenceModel<ComposerPK>	{
	/**
	 * Creates a new instance of ComposerModel
	 */
	public ComposerModel() { super();		}
	
	/**
	 * Creates a new instance of ComposerModel
	 * 
	 * @param id
	 * @param name
	 * @param address
	 * @param phoneno 
	 */
	public ComposerModel(String id,
								String name) {
		this(new ComposerPK(id), name);
	}

	/**
	 * Creates a new instance of ComposerModel
	 * 
	 * @param primarykey
	 * @param name
	 * @param address
	 * @param phoneno 
	 */
	public ComposerModel(ComposerPK primarykey,
								String name) {
		super();
		setPrimarykey(primarykey);
		setName(name);
		
	}
	
	
	/* ACCESSORS	--------------------------------------------------	*/
	public String getid()				{ return getPrimarykey().getid();	}
 	public String getName()					{ return name;									}
									


	/* MODIFIERS	--------------------------------------------------	*/
	public void setName(String name)				{ this.name = name;					}
				

	
	/* ATTRIBUTES	--------------------------------------------------	*/
	/** Name of this Composer.														*/
 	private String name;
 	private String id;
 	
}
