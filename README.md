# BaseFrame-BF-SPRINGMVC

### 框架提供的功能
>- 事务控制
>- Quartz定时器的集成
>- Shiro权限控制的集成
>- 支持Shiro权限标签@RequiresPermissions和@RequiresRoles
>- 通过@ValidationField进行属性的合法性校验
>- 通过@DictionaryField进行数据字典的转换
>- 通过日志切面来进行日志的统一打印
>- controller的切面，捕获异常并转换为ResultBean返回给前端，这样Controller层的代码只需要处理参数校验并调用service层进行逻辑处理
>- Redis缓存的集成
>- Spring Cache框架的集成
>- 工作流的集成
>- Swagger框架的集成并提供swagger-ui.html页面，方便查看接口信息
>- 公共的增删改查已封装到BaseService以及BaserRestServer的封装，只需要继承就可以实现相应的增删改查方法
>- 分页查询
>- Spring rest Template的集成，方便后台向第三方服务发送restful请求
>- 文件上传下载的接口集成，上传时支持秒传以及断点续传，下载时支持图片以及中文文件名


### BaseRestService提供的接口
- getPage               分页查询
- getAll                查询所有
- getByWhere            通过条件查询
- getById               通过主键ID查询
- getByName             通过名称查询
- isExist               通过条件判断是否存在
- removeById            通过ID进行逻辑删除
- batchRemove           逻辑批量删除
- removeFromDbById      通过ID进行物理删除
- batchRemoveFromDb     物量批量删除
- removeByWhere         通过条件进行删除
- removeByWhereFromDb   通过条件进行逻辑删除
- save                  保存
- modify                修改
- batchModify           批量修改