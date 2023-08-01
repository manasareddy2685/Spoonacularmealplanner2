package Uiwebpages;

public class Shoppingcart {
    private int id;
        private String title;
        private String image;
        private int likes;
        private double cups;
        private int gramsPerCup;

        // Constructor
        public Shoppingcart(int id, String title, String image, int likes, double cups, int gramsPerCup) {
            this.id = id;
            this.title = title;
            this.image = image;
            this.likes = likes;
            this.cups = cups;
            this.gramsPerCup = gramsPerCup;
        }

        // Getters and Setters
        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public int getLikes() {
            return likes;
        }

        public double getCups() {
            return cups;
        }

        public int getGramsPerCup() {
            return gramsPerCup;
        }

        public double getTotalGrams() {
            return cups * gramsPerCup;
        }

        @Override
        public String toString() {
            return "ID: " + id + "\nTitle: " + title + "\nImage: " + image + "\nLikes: " + likes +
                    "\nCUPS(1 cup eq to 100 gm): " + cups + "\nGRAMS: " + getTotalGrams() + "\n";
        }
    }
