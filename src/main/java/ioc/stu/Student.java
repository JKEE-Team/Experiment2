package demo.ioc.stu;

public class Student { //����ѧ���࣬��ѧ���������в���
 
	  private String name;
	  private String add;
	  
	  public void selfIntroDuction(){
	        System.out.println("���� " + name + " ���� " + add+" �ܸ�����ʶ�㣡");
	   
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
