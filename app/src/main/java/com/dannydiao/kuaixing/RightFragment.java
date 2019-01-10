package com.dannydiao.kuaixing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

public class RightFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String MARKDOWN =
          "# 快行\n" +
                  "> 快行是一个面向算法的路线推荐软件，基于我们设计的先进的巢芯算法.\n" +
                  "\n" +
                 // "<img src='http://thyrsi.com/t6/637/1545196790x2728306557.png' width = \"30%\" height = \"30%\"> 　　　　　 <img src='http://thyrsi.com/t6/637/1545196820x2728306557.png' width = \"30%\" height = \"30%\">\n" +
                  "\n" +
                  "\n" +
                  "## 更新日志\n" +
                  "#### **Version 0.4.1**　　　1月10日\n" +
                  "+ **增加了等待Progressbar**\n" +
                  "\n" +
                  "\n" +
                  "\n" +
                  "\n" +
                  "#### **Version 0.4.0**　　　1月10日\n" +
                  "+ **成功实现了路线规划的基本功能**\n" +
                  "\n" +
                //  "<img src='http://thyrsi.com/t6/653/1547111627x2890174106.png' width = \"25%\" height = \"25%\">\n" +
                  "#   \n" +
                  "\n" +
                  "\n" +
                  "#### **Version 0.3.0**　　　12月19日\n" +
                  "+ **打通了高德路线规划SDK的通路，成功获取到数据**\n" +
                  "\n" +
                //  "<img src='http://thyrsi.com/t6/637/1545196971x2728306557.png' width = \"25%\" height = \"25%\">\n" +
                  "#   \n" +
                  "\n" +
                  "#### **Version 0.2.2**　　　12月10日\n" +
                  "+ **完成关于页面的设计,基于开源框架RichText**\n" +
                  "#   \n" +
                  "\n" +
                  "#### **Version 0.2.1**　　　12月10日\n" +
                  "+ **实现了NavigationMenu的切换逻辑（with Fragment)**\n" +
                  "+ **构建了未来的路线规划和关于模块，填充即可**\n" +
                  "#              \n" +
                  "\n" +
                  "#### **Version 0.2.0**　　　12月10日\n" +
                  "+ **使用Fragment + NavigationMenu重构了界面（完成了Flag）**\n" +
                  "+ **编写了Util工具类，内含一些常用工具方法**\n" +
                  "+ **现在，开启App弹出的版本号Toast调用自Util.getVerName方法，并能自适应版本**\n" +
                  "\n" +
                  "#          \n" +
                  "\n" +
                  "\n" +
                  "#### **Version 0.1.3**　　　12月6日\n" +
                  "+ **将状态栏隐藏，使界面更一体化**\n" +
                  "+ **开启应用时弹出当前版本号提示**\n" +
                  "\n" +
                  "---\n" +
                  "#### **未来计划：**\n" +
                  "  #### 1.在12.30前完成Beta版本\n" +
                  "  #### 2.完成路线页面\n" +
                  "  #   \n" +
                  "---\n" +
                  "#### 本项目Github地址： [DannyDiao/KuaiXing](https://github.com/DannyDiao/KuaiXing)\n" +
                  "\n" +
                  "#### 当前版本：0.4.0\n" +
                  "#### 编译于2019/01/10 17:14\n";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right,container,false);

        TextView textView = view.findViewById(R.id.markdown);
        RichText.fromMarkdown(MARKDOWN).into(textView);
        return view;
    }

    public RightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RightFragment newInstance(String param1, String param2) {
        RightFragment fragment = new RightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}
