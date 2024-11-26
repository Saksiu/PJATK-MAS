
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class EndorsementDeal
{
    private static LinkedList<EndorsementDeal> EndorsementDealExtension =new LinkedList<>();

    public Player endorsingPlayer;
    public Company endorsedCompany;

    public Agent signingAgent;
    public float budget;
    public String signedAt;
    public int duration;

    public static String ExtensionToString(){
        return "Deals: \n"+(EndorsementDealExtension.isEmpty()?"":EndorsementDealExtension.toString());}


    private EndorsementDeal(Player endorsingPlayer, Company endorsedCompany, Agent signingAgent, float budget, String signedAt, int duration)
    {
        this.endorsingPlayer = endorsingPlayer;
        this.endorsedCompany = endorsedCompany;
        this.signingAgent = signingAgent;
        this.budget = budget;
        this.signedAt = signedAt;
        this.duration = duration;
    }
    public static String ConstructionFormat(){
        return "<Endorsing Player> <Endorsed Company> <Budget> <Sign Date> <Duration (in months)>";
    }

    public static EndorsementDeal Create(String[] rawData) throws IllegalArgumentException{
        EndorsementDeal created=new EndorsementDeal(
                Player.tryGetByName(rawData[0]),
                Company.tryGetByName(rawData[1]),
                Player.tryGetByName(rawData[0]).representingAgent,
                Float.parseFloat(rawData[2]),
                rawData[3],
                Integer.parseInt(rawData[4])
        );
        EndorsementDealExtension.add(created);
        return created;
    }

    public static EndorsementDeal[] getDealsBy(Player endorsingPlayer){
        return EndorsementDealExtension.stream()
                .filter(endorsementDeal -> endorsementDeal.endorsingPlayer.equals(endorsingPlayer))
                .toArray(EndorsementDeal[]::new);
    }

    public static EndorsementDeal[] getDealsBy(Company endorsedCompany){
        return EndorsementDealExtension.stream()
                .filter(endorsementDeal -> endorsementDeal.endorsedCompany.equals(endorsedCompany))
                .toArray(EndorsementDeal[]::new);
    }

    @Override
    public String toString()
    {
        return "EndorsementDeal{" +
                "endorsingPlayer: " + endorsingPlayer.name +
                ", endorsedCompany: " + endorsedCompany.name +
                ", signingAgent: " + signingAgent.name +
                ", budget: " + budget +
                ", signedAt: '" + signedAt + '\'' +
                ", duration: " + duration +
                '}';
    }
}
