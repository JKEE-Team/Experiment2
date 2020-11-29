package demo.ioc.stu;

public class Student { //建立学生类，用学生类来进行测试
 
	  private String name;
	  private String add;
	  
	  public void selfIntroDuction(){
	        System.out.println("我是 " + name + " 来自 " + add+" 很高心认识你！");
	   
	   }
	  
	  public String getName() {
	        return name;
	    }
 
	    public void setName(String name) {
	        this.name = name;
	    }
 
	    public String getAdd() {
	        return add;
	    }
 
	    public void setAdd(String add) {
	        this.add = add;
	    }
	  
}
