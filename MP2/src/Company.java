import java.util.Arrays;
import java.util.LinkedList;

public class Company
{
    private static LinkedList<Company> CompanyExtension=new LinkedList<>();

    public String name;

    private Company(String name)
    {
        this.name = name;
    }
    public static String ConstructionFormat(){
        return "<Name>";
    }

    public static String ExtensionToString(){
        return "Companies: \n"+(CompanyExtension.isEmpty()?"":CompanyExtension.toString());}

    public static Company Create(String[] rawData){

        CompanyExtension.forEach(company -> {
            if(company.name.equals(rawData[0]))
                throw new IllegalArgumentException("Company with name "+company.name+ " exists!");
        });

        Company created=new Company(rawData[0]);
        CompanyExtension.add(created);
        return created;
    }
    public static Company tryGetByName(String name) throws IllegalArgumentException{
        Company[] result= CompanyExtension.stream()
                .filter(company -> company.name.equals(name))
                .toArray(Company[]::new);
        if(result.length>1) throw new IllegalArgumentException("found more than one Company of name "+name);
        if(result.length<1) throw new IllegalArgumentException("found no Company of name "+name);
        //Main.log("tryGetByName returning "+result[0]);
        return result[0];
    }
    public EndorsementDeal[] getEndorsedDeals(){
        return EndorsementDeal.getDealsBy(this);
    }


    @Override
    public String toString() {return name + Arrays.toString(getEndorsedDeals());}
}
