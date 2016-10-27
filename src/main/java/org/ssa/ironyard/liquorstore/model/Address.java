package org.ssa.ironyard.liquorstore.model;

public class Address implements Cloneable
{
    String street;
    String city;
    ZipCode zip;
    State state;

    public String getStreet()
    {
        return this.street;
    }

    public String getCity()
    {
        return this.city;
    }

    public ZipCode getZip()
    {
        return this.zip;
    }

    public State getState()
    {
        return this.state;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setZip(ZipCode zip)
    {
        this.zip = zip;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public static class ZipCode
    {
        final String raw;

        public ZipCode(String raw)
        {
            // TODO reg
            raw = raw.replaceAll("[^0-9]", "");
            // then either 5 or 9 digit
            this.raw = raw;
        }

        public int length()
        {
            return this.raw.length();
        }

        /**
         *
         * @return a db-friendly format
         */
        public String datafy()
        {
            return this.raw;
        }
        
        public String getZipCode()
        {
            return this.raw;
        }

        @Override
        public String toString()
        {
            if (length() == 5)
                return this.raw;
            return this.raw.substring(0, 5) + "-" + this.raw.substring(5);
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((raw == null) ? 0 : raw.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ZipCode other = (ZipCode) obj;
            if (raw == null)
            {
                if (other.raw != null)
                    return false;
            }
            else if (!raw.equals(other.raw))
                return false;
            return true;
        }

    };

    public enum State
    {
        ALABAMA("AL", "Alabama"), ALASKA("AK", "Alaska"), ARIZONA("AZ", "Arizona"), // etc, etc
        CALIFORNIA("CA", "California"), COLORADO("CO", "Colorado"), CONNECTICUT("CT", "Connecticut"),
        DELAWARE("DE", "Delaware"), DISTRICT_OF_COLUMBIA("DC", "District of Columbia"),
        FLORIDA("FL", "Florida"), GEORGIA("GA", "Georgia"), HAWAII("HI", "Hawaii"),
        IDAHO("ID", "Idaho"), ILLINOIS("IL", "Illinois"), INDIANA("IN", "Indiana"),
        IOWA("IA", "Iowa"), KANSAS("KS", "Kansas"), KENTUCKY("KY", "Kentucky"),
        LOUISIANA("LA", "Louisiana"), MAINE("ME", "Maine"), MARYLAND("MD", "Maryland"),
        MASSACHUSETTS("MA", "Massachusetts"), MINNESOTA("MN", "Minnesota"), MISSISSIPPI("MS", "Mississippi"),
        MISSOURI("MO", "Missouri"), MONTANA("MT", "Montana"), NEBRASKA("NE", "Nebraska"),
        NEVADA("NV", "Nevada"), NEW_HAMPSHIRE("NH", "New Hampshire"), NEW_JERSEY("NJ", "New Jersey"),
        NEW_MEXICO("NM", "New Mexico"), NEW_YORK("NY", "New York"), NORTH_CAROLINA("NC", "North Carolina"),
        NORTH_DAKOTA("ND", "North Dakota"), OHIO("OH", "Ohio"), OKLAHOMA("OK", "Oklahoma"),
        OREGON("OR", "Oregon"), PENNSYLVANIA("PA", "Pennsylvania"), PUERTO_RICO("PR", "Puerto Rico"),
        RHODE_ISLAND("RI", "Rhode Island"), SOUTH_CAROLINA("SC", "South Carolina"), SOUTH_DAKOTA("SD", "South Dakota"),
        TENNESSEE("TN", "Tennessee"), TEXAS("TX", "Texas"), UTAH("UT", "Utah"), VERMONT("VT", "Vermont"),
        VIRGINIA("VA", "Virginia"), WASHINGTON("WA", "Washington"), WEST_VIRGINIA("WV", "West Virginia"),
        WISCONSIN("WI", "Wisconsin"), WYOMING("WY", "Wyoming");

        private final String abbreviation, fullText;

        private State(String abbreviation, String fullText)
        {
            this.abbreviation = abbreviation;
            this.fullText = fullText;
        }

        public String getAbbreviation()
        {
            return this.abbreviation;
        }

        public String getFullText()
        {
            return this.fullText;
        }

        public static State getInstance(String abbreviation)
        {
            for (State state : State.values())
            {
                if (state.abbreviation.equals(abbreviation))
                    return state;
            }
            return null;
        }

    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((zip == null) ? 0 : zip.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (city == null)
        {
            if (other.city != null)
                return false;
        }
        else if (!city.equals(other.city))
            return false;
        if (state != other.state)
            return false;
        if (street == null)
        {
            if (other.street != null)
                return false;
        }
        else if (!street.equals(other.street))
            return false;
        if (zip == null)
        {
            if (other.zip != null)
                return false;
        }
        else if (!zip.equals(other.zip))
            return false;
        return true;
    }

    @Override
    public  Address clone() 
    {
        try
        {
            return (Address) super.clone();
        }
        catch (CloneNotSupportedException ex)
        {
            return null;
        }
    }

    
}
