package demo.ioc.stu;

public class Student {
 
	  private String name;
	  private String add;
	  
	  public void selfIntroDuction(){
	        System.out.println("�ҵ������� " + name + " ������ " + add);
	   
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
