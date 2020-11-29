package view;

/**
 * ��ͼģ��
 **/
public class View {

    private String url;//��ת·��

    private String dispathAction = DispatchActionConstant.FORWARD;//��ת��ʽ

    public View(String url) {
        this.url = url;
    }

    public View(String url,String name,Object value) {
        this.url = url;
        ViewData view = new ViewData();
        view.put(name, value);
    }


    public View(String url,String name,String dispathAction ,Object value) {
        this.dispathAction = dispathAction;
        this.url = url;
        ViewData view = new ViewData();//�뿴����Ĵ���
        view.put(name, value);
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getDispathAction() {
        return dispathAction;
    }

    public void setDispathAction(String dispathAction) {
        this.dispathAction = dispathAction;
    }
}