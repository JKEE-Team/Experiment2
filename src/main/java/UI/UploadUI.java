package UI;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;

import org.apache.tomcat.util.http.fileupload.RequestContext;

//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import java.io.File;

import java.io.IOException;
import context.WebContext;
import annotation.Controller;
import annotation.RequestMapping;
import view.View;

@Controller
public class UploadUI {
	private static final long serialVersionUID = 1L;
    
    // �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "uploadFiles";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    //ʹ��RequestMappingע��ָ��forward1�����ķ���·��
    @RequestMapping("UploadUI/Uploadf")
    public View fileUpload() throws IOException {
    	System.out.println("map succeed");
    	HttpServletRequest request = WebContext.requestHodler.get();
    	HttpServletResponse response = WebContext.responseHodler.get();
    	if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
//            PrintWriter writer = response.getWriter();
//            writer.println("Error: ��������� enctype=multipart/form-data");
//            writer.flush();
            return new View("/upload.jsp");
        }
    	System.out.println("get form data!");
    	// �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);    
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);     
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8"); 
        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
//        String uploadPath = request.getServletContext().getRealPath("./")  + UPLOAD_DIRECTORY;
        String uploadPath = "C:\\Users\\ASUS\\Desktop\\������\\Java��ҵ��Ӧ��\\ʵ��\\ʵ��2\\���빤��2\\spring_mvc_v1\\" +UPLOAD_DIRECTORY;
        System.out.print("uploadpath: ");
        System.out.println(uploadPath);//D:\\Program\\Javap\\spring_mvc_v0\\
        String msg = "";
        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
        	System.out.println("����Ŀ¼�ɹ�");
            uploadDir.mkdir();
        }
        
        try {
            // ���������������ȡ�ļ�����

            List<FileItem> formItems = upload.parseRequest(request);
            System.out.println("��ȡ�ļ�... ");
            if (formItems != null && formItems.size() > 0) {
                // ����������
            	System.out.println("��ȡ���ɹ��� ");
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        msg = "�ļ��ϴ��ɹ�,"+"����·��: "+uploadPath;
                    }
                }
            }
            System.out.println("�ļ�Ϊ�գ� ");
        } catch (Exception ex) {
        	 msg = "������Ϣ: " + ex.getMessage();
        }
        // ��ת�� message.jsp
        
        return new View("/upload.jsp","msg",msg);
    	
    }

}
