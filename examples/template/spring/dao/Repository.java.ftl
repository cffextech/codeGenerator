package dao;
import java.util.List;

import javax.inject.Named;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cffex.demo.advanced.bean.AdvancedToDo;
import com.cffex.framework.annotation.EntityClass;
import com.cffex.framework.annotation.Gateway;
import com.cffex.framework.customRepository.FrameworkRepository;

<#--创建一个驼峰命名法的宏-->
@Named(value="${tableName?cap_first}Repository")
@EntityClass(entityClass=${tableName?cap_first}.class)
@Gateway
public interface ${tableName?cap_first}Repository extends CrudRepository<${tableName?cap_first}, Long>,
		FrameworkRepository<${tableName?cap_first}, Long> {

	//根据Id查询数据
	public ${tableName?cap_first} findById(Long Id);

	<#--这里要写成一个循环-->
	//根据title字段查询
	public List<${tableName?cap_first}> findByTitle(String title);

	//实体类的变量名中包含大写字母的情况
	public List<${tableName?cap_first}> findByCreatorId(Long creatorId);

	//like查询
	public List<${tableName?cap_first}> findByTitleLike(String title);

	//containing查询
	public List<${tableName?cap_first}> findByTitleContaining(String title);

	//自定义SQL查询
	@Query("select t from ${tableName?cap_first} t,Creator c where t.creatorId = c.id and c.name = :creatorName")
	public List<${tableName?cap_first}> findToDosByCreatorName(@Param("creatorName")String creatorName);

	//分页查询全部数据
	public Iterable<${tableName?cap_first}> findAll(Pageable pageable);

	//根据title字段分页查询
	public Page<${tableName?cap_first}> findByTitle(String title, Pageable pageable);

	//分页查询由某一creator建立的数据
	public Page<${tableName?cap_first}> findByCreatorId(Long creatorId, Pageable pageable);

}
