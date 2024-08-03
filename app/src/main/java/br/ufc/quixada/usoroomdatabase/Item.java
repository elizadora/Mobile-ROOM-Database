package br.ufc.quixada.usoroomdatabase;

public class Item {
    String name;
    String course;
    int age;

    public Item(String name, String course, Integer age){
        this.name = name;
        this.course = course;
        this.age = age;
    }

    public String getName(){
        return this.name;
    }

    public String getCourse(){
        return this.course;
    }

    public int getAge(){
        return this.age;
    }

    void setName(String name){
        this.name = name;
    }

    void setCourse(String course){
        this.course = course;
    }

    void setAge(int age){
        this.age = age;
    }
}
