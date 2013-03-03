transfer_wordpress_data
=======================

#迁移wordpress的博客数据到github上。  Transfer ur wordpress post data to github 
****
+ 先用phpmyadmin 导出原wordpress的数据（注意根据实际情况修改数据表的前缀）
import your origin blog data with phpmyadmin  
-导出的sql：   
  SELECT 
  IFNULL((
  SELECT group_concat( term1.name, ' ' )
  FROM puxterm_relationships ship1
  INNER JOIN pux_term_taxonomy tax1 ON tax1.term_taxonomy_id = ship1.term_taxonomy_id
  INNER JOIN pux_terms term1 ON term1.term_id = tax1.term_id
  WHERE p.id = ship1.object_id
  AND tax1.taxonomy = 'post_tag'), '')as post_tag,
  IFNULL((SELECT  term1.name  
  FROM pux_term_relationships ship1
  INNER JOIN pux_term_taxonomy tax1 ON tax1.term_taxonomy_id = ship1.term_taxonomy_id
  INNER JOIN pux_terms term1 ON term1.term_id = tax1.term_id
  WHERE p.id = ship1.object_id
  AND tax1.taxonomy = 'category' limit 0,1),'') as categries
  ,p.post_title, p.post_date, p.post_content, p.post_status AS post_type from pux_posts  as p   
-导出的时候记得用自定义导出，取消创建表、存储过程等，导出的格式选择xml(生成文章的格式是xml)
  
+ 用eclipse 打开项目， 打开代码中的TransferTestMain.java文件。修改输入和输出文件的路径  
（ INPUT_FILE=上步导出的xml文件；OUTPUT_DIR =你需要导出的目录，可以是_post/的绝对路径）
  
+ 编译运行程序，数据生成成功！
  
    
#  此方法在ubuntu下成功，不过还存在一些小bug（主要是迁移后文章内容有些不兼容的问题）。  
希望大家加入此项目。为github， 为jekyll贡献一己之力！  
  by  andwp  
  http://andwp.net/  
  <pcodezui@gmail.com>  
   2013-03-03
  
