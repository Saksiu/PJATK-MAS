import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Agent
{
    public static LinkedList<Agent> AgentExtension =new LinkedList<>();

    public String name;

    private LinkedList<Player> representedPlayers=new LinkedList<>();

    public Agent(String name)
    {
        this.name = name;
        AgentExtension.add(this);
    }

    public void representPlayer(Player player)throws IllegalArgumentException{
        //Main.log("represented players after adding player:\n"+ representedPlayers);
        if(player==null)
            throw new IllegalArgumentException("Player is null!");

        if(representedPlayers.contains(player))
            return;

        //remove the player from previous agent and assign to this
        if(player.isRepresented()&&player.representingAgent!=this)
            player.representingAgent.unRepresentPlayer(player);

        representedPlayers.add(player);
        //Main.log("represented players after adding player:\n"+ representedPlayers);
        player.setRepresentingAgent(this);
    }

    public void unRepresentPlayer(Player player){
        if(player==null||player.representingAgent!=this) return;

        representedPlayers.remove(player);
        player.removeCurrentlyRepresentingAgent();
    }
    public boolean representsPlayer(Player player){
        return representedPlayers.contains(player);
    }

    public static Agent Create(String[] rawData)throws IllegalArgumentException{
        Agent created=new Agent(rawData[0]);
        //Main.log("on Agent Create: "+Arrays.toString(rawData));
        for(int i=1;i<rawData.length;i++)
            created.representPlayer(Player.tryGetByName(rawData[i]));
        return created;
    }

    public static String ConstructionFormat(){
        return "<Name> Opt:<Represented Player1> <Represented Player2> ...";}

    public static String ExtensionToString(){
        return "Agents: \n"+(AgentExtension.isEmpty()?"":AgentExtension.toString());}

    @Override
    public String toString() {return name;}

    public static Agent tryGetByName(String name) throws IllegalArgumentException{
        Agent[] result= AgentExtension.stream()
                .filter(agent -> agent.name.equals(name))
                .toArray(Agent[]::new);
        if(result.length>1) throw new IllegalArgumentException("found more than one Agent of name "+name);
        if(result.length<1) throw new IllegalArgumentException("found no Agent of name "+name);
        return result[0];
    }
}
