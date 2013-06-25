package Framework.PageBuilder;

public class RR {
//    /// <summary>
//    /// Page load
//    /// </summary>
//    /// <param name="sender"></param>
//    /// <param name="e"></param>
//    protected virtual void Page_Load(object sender, EventArgs e)
//    {
//        string ret = "";
//        int i = 0;
//        //取得傳入值，用URL傳入值
//        string inData = Manuan.eBridge.Framework.Helper.Tools.Stream2String(Request.InputStream);
//        string _className = "", _methodName = "";
//        if (!string.IsNullOrEmpty(inData))
//        {
//            //未加密
//            if (inData.Substring(0, 1) == "<" && inData.Substring(inData.Length - 1, 1) == ">")
//            {
//                //inData = Manuan.eBridge.Framework.Security.DES.uncypt(inData, "m.y.u.i$1.0");
//            }
//            else
//            {
//                inData = Manuan.eBridge.Framework.Security.DES.decrypt(inData, Manuan.eBridge.Framework.Helper.NumberConvert.getPrivateKey());
//            }
//            XmlDocument xParams = new XmlDocument();
//            try
//            {
//                //xParams="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><_root><_class>{0}</_class><_params>{1}<arg name=\"{0}\">{1}</arg></_params></_root>"
//                //xParams.Load(Request.InputStream);
//                xParams.LoadXml(inData);
//                //"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><_root><_class>{0}</_class><_params>{1}</_params></_root>";
//                _className = xParams.SelectSingleNode("/_root/_class").InnerText;
//                _methodName = _className.Substring(_className.LastIndexOf('.') + 1);
//                _className = _className.Substring(0, _className.LastIndexOf('.'));
//
//            }
//            catch (Exception ex)
//            {
//                ret = ex.ToString();
//            }
//            //int paramLen = sdDataIn.Count - 2;//className and methodName
//            string[] parameters = new string[xParams.SelectSingleNode("/_root/_params").ChildNodes.Count];
//            foreach (XmlNode nodeArg in xParams.SelectSingleNode("/_root/_params").ChildNodes)
//            {
//                parameters[i] = nodeArg.InnerText;
//                i++;
//            }
//            if (!string.IsNullOrEmpty(_className) || !string.IsNullOrEmpty(_methodName))
//            {
//                ret = AjaxServer.GetInvoker().invoke(_className, _methodName, parameters);
//            }
//            else
//            {
//                ret = DirectCall(_methodName, parameters);
//            }
//        }
//        Response.Write(ret);
//        Response.End();
//
//    }
//
//    /// <summary>
//    /// 直接調用方法，用Case不用反射；
//    /// </summary>
//    /// <param name="s_methodName">方法名稱，以區分不同Case</param>
//    /// <param name="s_params">參數</param>
//    /// <returns></returns>
//    protected virtual string DirectCall(string s_methodName, string[] s_params)
//    {
//        string ret = "";
//        switch (s_methodName.ToLower())
//        {
//            case "":
//                break;
//            default:
//                break;
//        }
//        return ret;
//    }


}
