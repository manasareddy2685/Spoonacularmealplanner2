package Uiwebpages;

public class winepairing {

        private String wine;
        private boolean isPairingAvailable;

        // Constructor
        public winepairing(String wine) {
            this.wine = wine;
        }

        // Getter for wine
        public String getWine() {
            return wine;
        }

        // Setter for wine
        public void setWine(String wine) {
            this.wine = wine;
        }

        // Getter for isPairingAvailable
        public boolean isPairingAvailable() {
            return isPairingAvailable;
        }

        // Setter for isPairingAvailable
        public void setPairingAvailable(boolean pairingAvailable) {
            isPairingAvailable = pairingAvailable;
        }
    }


