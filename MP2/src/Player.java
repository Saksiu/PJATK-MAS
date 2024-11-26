import java.util.Arrays;
import java.util.LinkedList;

public class Player
{
    private static LinkedList<Player> PlayerExtension=new LinkedList<>();

    public String name;
    public int age;
    public Team currentTeam;
    public Agent representingAgent;

    public Player(String name, int age)
    {
        this.name = name;
        this.age=age;
        PlayerExtension.add(this);
    }

    public void setRepresentingAgent(Agent agent){
        if (representingAgent == null) {
            representingAgent = agent;
            agent.representPlayer(this);

        } else {
            // ... jeśli jest ten sam - okej
            // ... jeśli inny - usunąć siebie z listy tego i dodać do listy nowego
            if(agent!=representingAgent){
                representingAgent.unRepresentPlayer(this);
                representingAgent=agent;
            }
        }
    }

    public static Player Create(String[] rawData){
        if(playerWithNameExists(rawData[0]))
            throw new IllegalArgumentException("Player with name "+rawData[0]+" already exists!");

        Player created=new Player(rawData[0],Integer.parseInt(rawData[1]));
        String[] splitPlayerData;
        if(rawData.length>=3){
            splitPlayerData=rawData[2].split("@");

            if(splitPlayerData.length<2)
                throw new IllegalArgumentException("Didn't receive Team or Position "+rawData[2]);
            Team.tryGetByName(splitPlayerData[0]).addPlayer(Position.getPos(splitPlayerData[1]), created);
        }

        if(rawData.length>=4)
            Agent.tryGetByName(rawData[3]).representPlayer(created);

        return created;
    }

    public void addToTeam(Team newTeam, Position position)throws IllegalArgumentException{
        if(newTeam==null) throw new IllegalArgumentException("Team with provided name does not exist");

        if(newTeam==currentTeam) return;

        if(newTeam.isFull()&&!newTeam.containsPlayer(this)) throw new IllegalArgumentException("Cannot Add to Full Team "+this);
        //if(currentTeam!=null) return false;

        removeFromCurrentTeam();
        currentTeam=newTeam;

        if(newTeam.containsPlayer(this)) return;

        newTeam.addPlayer(position, this);

    }

    public void removeFromCurrentTeam(){
        if(currentTeam==null||!currentTeam.containsPlayer(this)) return;

        currentTeam.removePlayer(this);
        currentTeam=null;
    }

    private static boolean playerWithNameExists(String name){
        for(Player p : PlayerExtension)
            if(p.name.equals(name))
                return true;
        return false;
    }

    public static Player tryGetByName(String name) throws IllegalArgumentException{
        Player[] result= PlayerExtension.stream()
                .filter(player -> player.name.equals(name))
                .toArray(Player[]::new);
        if(result.length>1) throw new IllegalArgumentException("found more than one Agent of that name!");
        if(result.length<1) throw new IllegalArgumentException("found no Agent of that name!");
        //Main.log("tryGetByName returning "+result[0]);
        return result[0];
    }

    public static String ConstructionFormat(){
        return "<Name> <Age> OPT: <Current Team@Position> OPT: <Representing Agent>";
    }

    public static String ExtensionToString(){
        return "Players: \n"+(PlayerExtension.isEmpty()?"":PlayerExtension.toString());}

    public boolean isRepresented(){
        return representingAgent!=null;
    }

    public void removeCurrentlyRepresentingAgent(){
        if(representingAgent==null||!representingAgent.representsPlayer(this)) return;
        representingAgent.unRepresentPlayer(this);
        representingAgent=null;
    }

    public EndorsementDeal[] getEndorsedDeals(){
        return EndorsementDeal.getDealsBy(this);
    }

    @Override
    public String toString()
    {
        return  name +
                ", age: " + age +
                ", currentTeam: " + (currentTeam==null?"NONE":currentTeam.name) +
                ", representingAgent: " + (representingAgent==null?"NONE":representingAgent.name)+
                ", EndorsedDeals: " + Arrays.toString(EndorsementDeal.getDealsBy(this));
    }
}
