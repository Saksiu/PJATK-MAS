import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Team
{
    public static final int MAX_TEAM_SIZE=Position.values().length;
    private static LinkedList<Team> TeamExtension=new LinkedList<>();
    public String name;

    private Map<Position,Player> players=new HashMap<>();
    private LinkedList<Equipment> ownedEquipment=new LinkedList<>();

    public Team(String name)
    {
        this.name = name;
        TeamExtension.add(this);
    }
    public static Team Create(String[] rawData){

        TeamExtension.forEach(team -> {
            if(team.name.equals(rawData[0]))
                throw new IllegalArgumentException("Team with name "+team.name+ " exists!");
        });

        Team created=new Team(rawData[0]);
        String[] splitPlayerData;
        //Main.log(rawData.length);
        for(int i=1;i<rawData.length;i++){
            splitPlayerData=rawData[i].split("@");
            created.addPlayer(Position.getPos(splitPlayerData[1]), Player.tryGetByName(splitPlayerData[0]));
        }
        return created;
    }

    public boolean isFull(){return players.size()>=MAX_TEAM_SIZE;}

    public void addPlayer(Position position, Player player){

        if(teamContains(player)||isFull()) return;

        //remove player from their current team before assiging to this one
        if(player.currentTeam!=null&&player.currentTeam!=this)
            player.removeFromCurrentTeam();

        players.put(position,player);
        player.addToTeam(this,position);
    }

    public void removePlayer(Player player){
        if(player==null||!players.containsValue(player)) return;
        players.values().remove(player);
        player.removeFromCurrentTeam();
    }
    public boolean containsPlayer(Player player){
        return players.containsValue(player);
    }

    public void addEquipment(Equipment toAdd){
        if(!ownedEquipment.contains(toAdd))
            ownedEquipment.add(toAdd);
    }

    public static String ConstructionFormat(){
        return "<Name> <Player@Position> <Player@Position> ...";
    }

    public static String ExtensionToString(){
        return "Teams: \n"+(TeamExtension.isEmpty()?"":TeamExtension.toString());}

    public static Team tryGetByName(String name) throws IllegalArgumentException{
        Team[] result= TeamExtension.stream()
                .filter(team -> team.name.equals(name))
                .toArray(Team[]::new);
        if(result.length>1) throw new IllegalArgumentException("found more than one Team of name "+name);
        if(result.length<1) throw new IllegalArgumentException("found no Team of name "+name);
        return result[0];
    }

    private boolean teamContains(Player player){
        if(player==null) return false;
        return players.containsValue(player);
    }

    private Player findBy(Position position){
        return players.get(position);
    }


    @Override
    public String toString()
    {
        return  name +
                ", players: " + players +
                ", owned equipment: " + ownedEquipment;
    }
}
