package test;
import java.io.Serializable;
import java.util.Date;

public class Course implements Serializable{

        private int id;
        private String name;


        public void setid(int Id){
            this.id=id;
        }

        public int getId(){
            return this.id;
        }

        public void setname(String Name){
            this.name=name;
        }

        public String getName(){
            return this.name;
        }
}