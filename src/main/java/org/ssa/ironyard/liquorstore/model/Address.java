package org.ssa.ironyard.liquorstore.model;

public class Address 
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
            //TODO reg
            raw = raw.replaceAll("[^0-9]", "");
            //then either 5 or 9 digit
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

        @Override
        public String toString()
        {
            if (length() == 5)
                return this.raw;
            return this.raw.substring(0, 5) + "-" + this.raw.substring(5);
        }
        
        
    };
    
    public enum State
    {
        ALABAMA("AL", "Alabama"), ARIZONA("AZ", "Arizona"); //etc, etc
        
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
}
