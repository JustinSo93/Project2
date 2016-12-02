package marina;
import sql.CorePersistenceModel;

/**
 * ConductorModel represents the persistence model for a customer object.
 * @author Reg
 */
public class CompositionModel extends CorePersistenceModel<CompositionPK> {
	
		/**
		 * Creates a new instance of ConductorModel
		 */
		public CompositionModel() { super();		}
		
		/**
		 * Creates a new instance of ConductorModel
		 * 
		 * @param composer
		 * @param compositionName
		 */
		public CompositionModel(String composer, String compositionName) {
			this(new CompositionPK(composer), compositionName);
		}

		/**
		 * Creates a new instance of ConductorModel
		 * 
		 * @param primarykey
		 * @param compositionName
		 */
		public CompositionModel(CompositionPK primarykey,
									String compositionName) {
			super();
			setPrimarykey(primarykey);
			setcompositionName(compositionName);
			
		}
		
		
		/* ACCESSORS	--------------------------------------------------	*/
		public String getcomposer()				{ return getPrimarykey().getcomposer();	}
	 	public String getcompositionName()					{ return compositionName;									}



		/* MODIFIERS	--------------------------------------------------	*/
		public void setcompositionName(String compositionName)				{ this.compositionName = compositionName;					}


		
		/* ATTRIBUTES	--------------------------------------------------	*/
		/** compositionName of this composition.														*/
	 	private String compositionName;

	}


