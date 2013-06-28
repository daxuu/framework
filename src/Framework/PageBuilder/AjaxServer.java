package Framework.PageBuilder;

import java.util.*;
import java.lang.reflect.Constructor;  
import java.lang.reflect.Method;  

import Framework.Data.Global;
import Framework.logger.MyLogger;

/**
 * 
 * 
 * 项目名称：Framework 类名称：AjaxServer 类描述：通用Ajax通道，使用反射实现类的动态调用
 * 
 * @author lucky 创建时间：Jun 19, 2013 11:30:49 AM 修改人：lucky 修改时间：Jun 19, 2013
 *         11:30:49 AM 修改备注：
 * @version
 * 
 */
public class AjaxServer {
	private List<String> asssemblyList = new ArrayList<String>();
	//private HashMap(<String>,<Object>) typeList = new HashMap(<String>,<Object>)();
	private Map<String,Class<?>> classList =new HashMap<String,Class<?>>();
	private String user_msg = "向服務器请求失敗({0})。\r\n可能是数据服务器运行不正常！\r\n若有资料还未保存，请不要关闭当前系统画面；\r\n并及时联络系统管理人员！";
	private static AjaxServer invoker;

	// / <summary>
	// / 單例模式，所以設置成private，防止用於自行創建其實例
	// / </summary>
	private AjaxServer()
    {
        //取得web.config配置的程序集，可有多個，用逗号分隔
        String classStr = Global.AppConfig("ajax_dll_name");
        String[] dlls = classStr.split(",");
        Class<?> classer=null;

        for(int i = 0;i<dlls.length;i++){
        	 //asssemblyList.add();
             try{
                 //
             	classer=Class.forName(dlls[i]);
             	
             	classList.put(dlls[i], classer);
             	
             }catch(Exception e){
                 e.printStackTrace();
             }
        }

    }

	public static AjaxServer GetInvoker() {
		if (invoker == null)
			invoker = new AjaxServer();
		return invoker;
	}

	/**
	 * 
	 * 功能概述：动态调用 
	 * @param cName
	 * @param MethodName
	 * @param type
	 * @param param
	 * @return
	 * @author rayd 
	 * 创建时间：Jun 28, 2013 5:14:25 PM  
	 * 修改人：rayd
	 * 修改时间：Jun 28, 2013 5:14:25 PM  
	 * 修改备注：  
	 * @version 1.0 
	 *
	 */
	public Object invoke(String cName, String MethodName, String[] type, String[] param) {
		
		Object retobj = null;
		try {
			// 加载指定的Java类
			//Class<?> cls = Class.forName(cName);
			Class<?> cls = classList.get(cName);
			
			// 获取指定对象的实例
			Constructor<?> ct = cls.getConstructor(null);
			Object obj = ct.newInstance(null);

			// 构建方法参数的数据类型
			Class<?> partypes[] = this.getMethodClass(type);

			// 在指定类中获取指定的方法
			Method meth = cls.getMethod(MethodName, partypes);
			//cls.getMethod(name, parameterTypes)
			// 构建方法的参数值
			Object arglist[] = this.getMethodObject(type, param);

			// 调用指定的方法并获取返回值为Object类型
			retobj = meth.invoke(obj, arglist);

		} catch (Throwable e) {
			System.err.println(e);
		}
		return retobj;
	}
	
	public Object invoke(String cName, String MethodName,  String[] param) {
		String[] types = new String[param.length];
		for(int i=0;i<param.length;i++){
			types[i]="string";
		}
		
		return invoke(cName, MethodName,types, param);
	}

	// 获取参数类型Class[]的方法
	public Class<?>[] getMethodClass(String[] type) {
		Class<?>[] cs = new Class[type.length];
		for (int i = 0; i < cs.length; i++) {
			if (!type[i].trim().equals("") || type[i] != null) {
				if (type[i].equals("int") || type[i].equals("Integer")) {
					cs[i] = Integer.TYPE;
				} else if (type[i].equals("float") || type[i].equals("Float")) {
					cs[i] = Float.TYPE;
				} else if (type[i].equals("double") || type[i].equals("Double")) {
					cs[i] = Double.TYPE;
				} else if (type[i].equals("boolean")
						|| type[i].equals("Boolean")) {
					cs[i] = Boolean.TYPE;
				} else {
					cs[i] = String.class;
				}
			}
		}
		return cs;
	}

	// 获取参数Object[]的方法
	public Object[] getMethodObject(String[] type, String[] param) {
		Object[] obj = new Object[param.length];
		for (int i = 0; i < obj.length; i++) {
			if (!param[i].trim().equals("") || param[i] != null) {
				if (type[i].equals("int") || type[i].equals("Integer")) {
					obj[i] = new Integer(param[i]);
				} else if (type[i].equals("float") || type[i].equals("Float")) {
					obj[i] = new Float(param[i]);
				} else if (type[i].equals("double") || type[i].equals("Double")) {
					obj[i] = new Double(param[i]);
				} else if (type[i].equals("boolean")
						|| type[i].equals("Boolean")) {
					obj[i] = new Boolean(param[i]);
				} else {
					obj[i] = param[i];
				}
			}
		}
		return obj;
	}

//	// / <summary>
//	// / 用反射調用指定類別的方法
//	// / </summary>
//	// / <param name="className">類名</param>
//	// / <param name="method">方法名稱</param>
//	// / <param name="parameters">參數值集合，前端傳入均為文字形態，在此方法中將自動轉換對於參數的類型</param>
//	// / <returns>返回該方法的類型</returns>
//	public String invoke(String className, String method, String[] parameters)
//    {
//        object ret;
//        //object[] args = new object[parameters.Length];
//        String classFullName;
//        String result = "";
//        //找出傳入的類的全限定名(eg CSharpLearn.Teacher,CSharpLearn)
//        List<String> fullNames = typeList.FindAll(delegate(String fullNameWithAssemblyName)     
//        {
//            //分隔類名和程序集名，取得類名(含命名空間)
//            String fullName = fullNameWithAssemblyName.Split(new char[] { ',' })[0];         
//            if (fullName.IndexOf(className) == -1)
//                return false;
//            String[] tempFullName = fullName.Split(new char[] { '.' });
//            String[] tempClassName = className.Split(new char[] { '.' });
//            if (tempFullName.Length < tempClassName.Length)
//                return false;
//            for (int i = tempFullName.Length - 1, j = tempClassName.Length - 1; i >= 0 && j >= 0; i--, j--)
//            {
//                if (tempClassName[j] != tempFullName[i])
//                    return false;
//            }
//            return true;
//        });
//
//
//        if (fullNames.Count == 0)
//        {
//            MyLogger.Log(String.format("找不到類型名:%1$s，請提供正確的類型名!", className));
//            return String.format(user_msg, method);
//        }
//        else if (fullNames.Count > 1)
//        {
//            MyLogger.Log(String.format("找到多個類型名::%1$s，請加入命名空間以縮小範圍", className));
//            return String.format(user_msg, method);
//        }
//        else
//        {
//            classFullName = fullNames[0];
//        }
//        Type t = Type.GetType(classFullName);
//        MethodInfo methodInfo;
//        //若是同名重載，取出
//        if (parameters.Length > 0)
//        {
//            methodInfo = t.GetMethod(method, getParamTypes(parameters));
//        }
//        else
//        {
//            methodInfo = t.GetMethod(method);
//        }
//        if (methodInfo == null)
//        {
//
//            MyLogger.Log(String.format("無此類別成員：{0},請確認參數個數與方法名稱是否匹配，參數：{1}", method, unboxParams(parameters)));
//            return String.format(user_msg, method);
//        }
//        //args = getParams(methodInfo, method, parameters);
//
//        Type returnType = methodInfo.ReturnType;
//        object o = null;
//        //如果要調用的方法不是靜態的，就創建實例；如果是靜態的，則保留null值。
//        if (!methodInfo.IsStatic)            
//            o = Activator.CreateInstance(t);
//        try
//        {
//            MyLogger.Write(classFullName + ":" + method, "", "invoke", "");
//            //ret = methodInfo.Invoke(o, args);
//            //全部参数均使用String，可省类型节省转换开销
//            if (parameters.Length > 0)
//            {
//                ret = methodInfo.Invoke(o, parameters);
//            }
//            else
//            {
//                ret = methodInfo.Invoke(o,null);
//            }
//        }
//        catch (Exception ex)
//        {
//
//            Framework.MyLogger.Log(String.format("調用此方法失敗：{0}，錯誤信息：{1}", classFullName + ":" + method, ex.Message + unboxParams(parameters)));
//            return String.format(user_msg, method);
//        }
//
//        if (ret != null)
//        {
//            if (returnType == typeof(DataTable))       //如果返回的是DataTable
//            {
//                result = Helper.Tools.WriteDataTable(ret as DataTable);          //轉化成json字符串
//                HttpContext.Current.Response.ContentType = "application/json";
//                return result;
//            }
//            else if (returnType == typeof(DataSet))    //如果返回的是DataSet
//            {
//                result = Helper.Tools.WriteDataSet(ret as DataSet);          //轉化成json字符串
//                HttpContext.Current.Response.ContentType = "application/json";
//                return result;
//            }
//            else if (returnType == typeof(XmlDocument) || returnType == typeof(Manuan.eBridge.Framework.Data.XmlDocModel))    //如果返回的是XmlDocument
//            {
//                result = Helper.Tools.XmlToJSON(ret as XmlDocument, "");          //轉化成json字符串
//                HttpContext.Current.Response.ContentType = "application/json";
//
//                return result;
//            }
//            else
//            {
//                return ret.ToString();
//            }
//        
//        }
//        else
//            return "";
//    }
	// private object[] getParams(MethodInfo argMethod, String argMetohdName,
	// String[] argParams)
	// {
	// object[] ret = new object[argParams.Length];
	//
	// //MethodInfo methodInfo = t.GetMethods(BindingFlags.
	// //轉換參數的類型
	// ParameterInfo[] paramsInfo = argMethod.GetParameters();
	// for (int m = 0; m < paramsInfo.Length; m++)
	// {
	// ret[m] = Convert.ChangeType(argParams[m], paramsInfo[m].ParameterType);
	// }
	//
	// return ret;
	// }

	// / <summary>
	// /
	// / </summary>
	// / <param name="argMethod"></param>
	// / <param name="argParams"></param>
	// / <returns></returns>
	// private Type[] getParamTypes(String[] argParams)
	// {
	// Type[] ret = new Type[argParams.Length];
	// for (int m = 0; m < argParams.Length; m++)
	// {
	// ret[m] = typeof(String);
	// }
	//
	// return ret;
	// }

	// private String unboxParams(String[] s_args)
	// {
	// int i = 1;
	// String result = "\r\n";
	// foreach (String param in s_args)
	// {
	// result += String.format("参数{0}:", i.ToString());
	// if (param == null)
	// {
	// result += "Null\r\n";
	// }
	// else
	// {
	// result += param + "\r\n";
	// }
	// i++;
	// }
	// return result;
	// }
}
