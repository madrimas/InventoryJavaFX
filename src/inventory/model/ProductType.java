package inventory.model;

/**
 * Created by madrimas on 20.03.2017.
 */
public enum ProductType {
    UNDEFINED,
    SHIRT{
        @Override
        public String toString(){
            return "Koszulka";
        }
    },
    SHORT {
        @Override
        public String toString() {
            return "Spodenki";
        }
    },
    SOCKS{
        @Override
        public String toString(){
            return "Getry";
        }
    },
    CAP{
        @Override
        public String toString(){
            return "Czapka";
        }
    }
}
