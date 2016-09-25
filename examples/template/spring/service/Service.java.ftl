package service;
import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cffex.demo.advanced.bean.${tableName?cap_first};
import com.cffex.demo.bean.${tableName?cap_first};
import com.cffex.framework.annotation.Forbid;
import com.cffex.framework.annotation.Gateway;
import com.cffex.framework.annotation.SessionAttribute;
@Gateway
@Forbid(forbiddenMethods = {"toBeForbided1","toBeForbided2"})
public interface ${tableName?cap_first}Service {

	//根据pageNum和pageSize查询分页数据
	public List<${tableName?cap_first}> findAllByPageNum(int pageNum, int pageSize);

	public List<${tableName?cap_first}> findByTitle(String title, int pageNum, int pageSize);

	//展示如何在service中使用session
	public String getSessionContent();

	//展示session中的值如何作为函数的参数
	public String getUserIdInSession(@SessionAttribute("userId") String userId);

	//展示session中的值如何作为函数的参数，当函数中存在@SessionAttribute注解的参数时，参数要作为最后的一个或几个参数。
	public String getUserIdInSession(String param, @SessionAttribute("userId") String userId);

	//获取configure.properties中的值
	public String getConfigProperties();

	//调用${tableName?cap_first}Service
	public List<${tableName?cap_first}> callService();

	//上传文件
	public void uploadFile(List<File> files);

	//上传文件附带其他参数
	//public void uploadFile(List<File> files, String param1, int param2);

	//下载文件
	public File downloadFile(String fileName);

	//展示禁止该函数的对外访问权限
	public String toBeForbided1();

	//展示禁止该函数的对外访问权限
	public String toBeForbided2();

	//展示如何记录日志
	public String loggerDemo();
}
