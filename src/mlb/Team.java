package mlb;
/**
 * @author Roman Yasinovskyy
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Team {
    // TODO: Use JsonProperty correctly
    
    private final String id;
    private final String abbreviation;
    private final String name;
    private final String conference;
    private final String division;
    private ArrayList<Player> roster;
    private Address address;
    private byte[] logo;
    /**
     * Default class constructor.
     * Needed to load json properly.
     */
    public Team() {
        id = "";
        abbreviation = "";
        name = "";
        conference = "";
        division = "";
        address = null;
    }
    /**
     * Class constructor
     * @param _id
     * @param _abbr
     * @param _name
     * @param _conf
     * @param _div
     */
    public Team(String _id, String _abbr, String _name, String _conf, String _div) {
        this.id = _id;
        this.abbreviation = _abbr;
        this.name = _name;
        this.conference = _conf;
        this.division = _div;
    }
    /**
     * @return Team id
     */
    @JsonProperty("team_id")
    public String getId() {
        return this.id;
    }
    /**
     * @return Team abbreviation
     */
    @JsonProperty("abbreviation")
    public String getAbbreviation() {
        return this.abbreviation;
    }
    /**
     * @return Team name
     */
    @JsonProperty("name")
    public String getName() {
        return this.name;
    }
    /**
     * @return Team conference
     */
    @JsonProperty("conference")
    public String getConference() {
        return this.conference;
    }
    /**
     * @return Team division
     */
    @JsonProperty("division")
    public String getDivision() {
        return this.division;
    }
    /**
     * @return Team roster
     */
    @JsonProperty("roster")
    public ArrayList<Player> getRoster() {
        return this.roster;
    }
    /**
     * Set team's roster
     * @param _roster 
     */
    public void setRoster(ArrayList<Player> _roster) {
        this.roster = _roster;
    }
    /**
     * @return Team address
     */
    @JsonProperty("address")
    public Address getAddress() {
        return this.address;
    }
    /**
     * Set team address
     * @param _address
     */
    public void setAddress(Address _address) {
        this.address = _address;
    }
    /**
     * @return Team logo
     */
    @JsonProperty("logo")
    public byte[] getLogo() {
        return this.logo;
    }
    /**
     * Set team logo
     * @param _logo 
     */
    public void setLogo(byte[] _logo) {
        this.logo = _logo;
    }
    /**
     * @return Team as a String
     */
    @Override
    public String toString() {
        // TODO: Implement this method
        Address address=this.getAddress();
        return address.getTeam()+'\n'+this.getConference()+" | "+this.getDivision()+"\n"+address.toString()+"\n"+"Roster size: "+this.getRoster().size();
        
        //throw new UnsupportedOperationException();
    }
}