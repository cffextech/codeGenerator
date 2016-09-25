package test;
import java.io.Serializable;
import java.util.Date;

public class Test2 implements Serializable{

        private int id;
        private int courseId;
        private String chapterName;


        public void setid(int Id){
            this.id=id;
        }

        public int getId(){
            return this.id;
        }

        public void setcourseId(int CourseId){
            this.courseId=courseId;
        }

        public int getCourseId(){
            return this.courseId;
        }

        public void setchapterName(String ChapterName){
            this.chapterName=chapterName;
        }

        public String getChapterName(){
            return this.chapterName;
        }
}