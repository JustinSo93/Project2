import sql.CorePersistenceModel;

public class MovementModel extends CorePersistenceModel<MovementPK>{
	
	public MovementModel(){super();}
	
	public MovementModel(String number, String mName){ 
		this (new MovementPK(number),mName);
	}
	
	public MovementModel(MovementPK pk, String name){
		super();
		setName(name);
		setPrimarykey(pk);
	}
	
	/* ACCESSORS	--------------------------------------------------	*/
	public String getNumber(){ return getPrimarykey().getNumber();}
	public String getName() {return this.getName(); }
	
	/* MODIFIERS	--------------------------------------------------	*/
	public void setName(String n) {this.movementName = n;}
	
	
	/* ATTRIBUTES	--------------------------------------------------	*/
	private String movementName;
}
