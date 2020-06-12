package game;

/**
 * A PortableItem that can reduce the damage caused by other weapons
 * @author Lai Ying Ying
 */
public class Armour extends PortableItem {
    private int count = 0;
    private final int max = 3;
    public Armour() {
        super("Armour", 'U');
    }

    public Boolean use(){
        System.out.println("COUNT:"+ count);
        count+=1;
        if(count>max){
            return false ;
        }
        return true;
    }




}
