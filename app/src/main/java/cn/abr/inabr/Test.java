package cn.abr.inabr;

import com.squareup.kotlinpoet.AnnotationSpec;
import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.FileSpec;
import com.squareup.kotlinpoet.FunSpec;
import com.squareup.kotlinpoet.KModifier;
import com.squareup.kotlinpoet.ParameterSpec;
import com.squareup.kotlinpoet.ParameterizedTypeName;
import com.squareup.kotlinpoet.PropertySpec;
import com.squareup.kotlinpoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.abr.inabr.utils.StringUtils;
import dagger.Module;
import dagger.Provides;

/**
 */
public class Test {

    private static String packageName = "cn.abr.inabr.";
    private static String ModulePackage = "cn.abr.inabr.dagger2.module";
    private static String ComponentPackage = "cn.abr.inabr.dagger2.component";
    private static String ModelPackage = "cn.abr.inabr.mvp.model";
    private static String PresenterPackage = "cn.abr.inabr.mvp.presenter";
    private static String ViewPackage = "cn.abr.inabr.mvp.view";
    private static String entityPackage = "cn.abr.inabr.entity.";

    private static List<FunSpec> funList = new ArrayList<>();
    private static List<FunSpec> presenterFun = new ArrayList<>();
    private static List<ParameterSpec> parameterSpecs = new ArrayList<>();
    private static List<ParameterSpec> presenterParameterSpecs = new ArrayList<>();

    private static String name="Video";

    public static void main(String[] args) {

       /* addFun("GET_RECOMMEND_LIST",false,"SelectionsListEntity","item","page");
        createModel(name);*/
        addViewFun("showList","SelectionsListEntity",true);
        createView(name);
        createModule(name, "SelectionsListEntity",false);
        createComponent(name,"VideoFragment",name+"Module");
        createPresenter(name, "SelectionsListEntity", false);
    }

    private static void addFun(String funName, boolean isList, String entityName, String... param) {
        funList.add(createModelFun(funName, isList, entityName, param));
        presenterFun.add(createPresenterFun(funName,isList,entityName,param));
    }

    public static String changeName(String name)
    {
        String[] split = name.toLowerCase().split("_");
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (i==0)
                stringBuffer.append(split[i]);
            else stringBuffer.append(capitalize(split[i]));
        }
        return stringBuffer.toString();
    }
    //首字母大写
    public static String capitalize(String name) {
        //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }
    private static FunSpec createModelFun(String funName, boolean isList, String classPath, String... param) {
        FunSpec.Builder funSpec = FunSpec.builder(changeName(funName));
        for (int i = 0; i <param.length ; i++) {
            String paramName = param[i];
            parameterSpecs.add(new ParameterSpec.Builder(paramName, ClassName.bestGuess(String.class.getSimpleName())).build());
            if (i ==param.length-1) {
                if (isList)
                    parameterSpecs.add(new ParameterSpec.Builder("callback",
                            ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.ResultCallback"),
                                    ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                                            ParameterizedTypeName.get(ClassName.bestGuess("List"),
                                                    ClassName.bestGuess(entityPackage + classPath))))).build());
                else parameterSpecs.add(new ParameterSpec.Builder("callback",
                        ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.ResultCallback"),
                                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                                        ClassName.bestGuess(entityPackage + classPath)))).build());
            }

        }

        funSpec.addParameters(parameterSpecs);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("val json = String.format(%T.");
        stringBuffer.append(funName).append(",");
        for (int i = 0; i < param.length; i++) {
            stringBuffer.append(param[i]);
            if (i != param.length - 1)
                stringBuffer.append(",");
            else
                stringBuffer.append(")");

        }
        parameterSpecs.clear();
        funSpec.addStatement(stringBuffer.toString(), ClassName.bestGuess(packageName + "common.Constants"))
                .addStatement("getResponse(%T.%N, json, callback)", ClassName.bestGuess(packageName + "common.Api"), funName+"_URL");
        return funSpec.build();
    }

    public static void createModel(String className) {

        FileSpec.Builder build = new FileSpec.Builder(ModelPackage, className + "Model");
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(className + "Model");

        typeSpec.primaryConstructor(FunSpec.constructorBuilder()
                .addAnnotation(Inject.class)
                .build())
                .superclass(ClassName.bestGuess(packageName + "base.BaseModel"));
        for (FunSpec funSpec : funList) {
            typeSpec.addFunction(funSpec);
        }
        parameterSpecs.clear();
        funList.clear();
        FileSpec fileSpec = build.addType(typeSpec.build()).build();
        File file = new File("app/src/main/java");
        try {
            fileSpec.writeTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createModule(String className, String entityName, boolean isList) {
        String viewName=className+"View";
        FileSpec.Builder build = new FileSpec.Builder(ModulePackage, className + "Module");
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(className + "Module");
        FunSpec.Builder constructor = FunSpec.constructorBuilder();
        ParameterizedTypeName notListParam = ParameterizedTypeName.get(ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName),
                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                        ClassName.bestGuess(entityPackage + entityName)));

        ParameterizedTypeName isListParam = ParameterizedTypeName.get(ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName),
                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                        ParameterizedTypeName.get(ClassName.bestGuess("List"),
                                ClassName.bestGuess(entityPackage + entityName))));


        if (viewName.equals("DataView")) {
            constructor.addParameter("view", isList ? isListParam : notListParam);
        } else
            constructor.addParameter("view", ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName));

        constructor.callSuperConstructor("constructor(view)")
                .addAnnotation(Inject.class)
                .build();
        typeSpec
                 .addAnnotation(Module.class)
                .primaryConstructor(constructor.build())
                .addSuperclassConstructorParameter("view")
                .superclass(ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseModule"),
                        viewName.equals("DataView") ? isList ? isListParam : notListParam : ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName)));

        FunSpec.Builder funSpec = FunSpec.builder("provideTempLateView");
        funSpec
                .addAnnotation(Provides.class)
                .returns(
                        viewName.equals("DataView") ? isList ? isListParam : notListParam : ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName)).addStatement("return view");
        typeSpec.addFunction(funSpec.build());

        FileSpec fileSpec = build.addType(typeSpec.build()).build();
        File file = new File("app/src/main/java");
        try {
            fileSpec.writeTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createComponent(String className, String injectName, String moduleName) {

        FileSpec.Builder build = new FileSpec.Builder(ComponentPackage, className + "Component");
        TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder(className + "Component");

        typeSpec
                .addAnnotation(Singleton.class)
                .addAnnotation(new AnnotationSpec.Builder(ClassName.bestGuess("dagger.Component")).addMember("modules = [(%T::class)]", ClassName.bestGuess(ModelPackage + "." + moduleName)).build());


        FunSpec.Builder funSpec = FunSpec.builder("inject");
        funSpec.addModifiers(KModifier.ABSTRACT);
        funSpec.addParameter(injectName, ClassName.bestGuess((injectName.endsWith("Activity") ? "cn.abr.inabr.activity." : "cn.abr.inabr.fragment.") + injectName));
        typeSpec.addFunction(funSpec.build());

        FileSpec fileSpec = build.addType(typeSpec.build()).build();
        File file = new File("app/src/main/java");
        try {
            fileSpec.writeTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addViewFun(String funName, String entityName, boolean isList) {
        funList.add(createViewFun(funName, entityName, isList));
    }

    public static FunSpec createViewFun(String funName, String entityName, boolean isList) {

        ParameterSpec parameterSpec = null;
        if (isList) {
            parameterSpec = new ParameterSpec.Builder("response",
                    ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                            ParameterizedTypeName.get(ClassName.bestGuess("List"),
                                    ClassName.bestGuess(entityPackage + entityName)))).build();
        } else parameterSpec = new ParameterSpec.Builder("response",
                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                        ClassName.bestGuess(entityPackage + entityName))).build();


        FunSpec.Builder funSpec = FunSpec.builder(funName);
        funSpec.addModifiers(KModifier.ABSTRACT);
        funSpec.addParameter(parameterSpec);

        return funSpec.build();
    }

    public static void createView(String className) {

        FileSpec.Builder build = new FileSpec.Builder(ViewPackage, className + "View");
        TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder(className + "View");

        for (FunSpec funSpec : funList) {
            typeSpec.addFunction(funSpec);
        }
        funList.clear();

        FileSpec fileSpec = build.addType(typeSpec.build()).build();
        File file = new File("app/src/main/java");
        try {
            fileSpec.writeTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createPresenter(String className, String entityName, boolean isList) {
        String viewName=className+"View";
        FileSpec.Builder build = new FileSpec.Builder(PresenterPackage, className + "Presenter");
        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(className + "Presenter");
        FunSpec.Builder constructor = FunSpec.constructorBuilder();
        ParameterizedTypeName notListParam = ParameterizedTypeName.get(ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName),
                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                        ClassName.bestGuess(entityPackage + entityName)));

        ParameterizedTypeName isListParam = ParameterizedTypeName.get(ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName),
                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                        ParameterizedTypeName.get(ClassName.bestGuess("List"),
                                ClassName.bestGuess(entityPackage + entityName))));

        if (viewName.equals("DataView")) {
            constructor.addParameter("mView", isList ? isListParam : notListParam);
        } else
            constructor.addParameter("mView", ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName));

        constructor.callSuperConstructor("constructor(view)")
                .addAnnotation(Inject.class)
                .build();
        typeSpec
                .primaryConstructor(constructor.build())
                .addSuperclassConstructorParameter("mView")
                .superclass(ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BasePresenter"),
                        viewName.equals("DataView") ? isList ? isListParam : notListParam : ClassName.bestGuess("cn.abr.inabr.mvp.view." + viewName)));

        PropertySpec.Builder model = new PropertySpec.Builder("model", ClassName.bestGuess("cn.abr.inabr.mvp.model." + className + "Model"));
        model.addModifiers(KModifier.LATEINIT)
                .mutable(true)
                .addAnnotation(Inject.class);
        typeSpec.addProperty(model.build());

        for (FunSpec funSpec : presenterFun) {
            typeSpec.addFunction(funSpec);
        }
        presenterParameterSpecs.clear();
        presenterFun.clear();

        FileSpec fileSpec = build.addType(typeSpec.build()).build();
        File file = new File("app/src/main/java");
        try {
            fileSpec.writeTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FunSpec createPresenterFun(String funName, boolean isList, String entityName, String... param) {

        ParameterizedTypeName notListParam = ParameterizedTypeName.get(ClassName.bestGuess("cn.abr.inabr.base.ResultCallback"),
                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                        ClassName.bestGuess(entityPackage + entityName)));

        ParameterizedTypeName isListParam = ParameterizedTypeName.get(ClassName.bestGuess("cn.abr.inabr.base.ResultCallback"),
                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                        ParameterizedTypeName.get(ClassName.bestGuess("List"),
                                ClassName.bestGuess(entityPackage + entityName))));






        FunSpec.Builder funSpec = FunSpec.builder(changeName(funName));
        for (int i = 0; i < param.length; i++) {
            String paramName = param[i];
            presenterParameterSpecs.add(new ParameterSpec.Builder(paramName, ClassName.bestGuess(String.class.getSimpleName())).build());
        }
        TypeSpec.Builder compare = TypeSpec.anonymousClassBuilder()
                .superclass(isList ? isListParam : notListParam)
                .addFunction(FunSpec.builder("onResponse")
                        .addModifiers(KModifier.OVERRIDE)
                        .addParameter("response",isList? ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                                ParameterizedTypeName.get(ClassName.bestGuess("List"),
                                        ClassName.bestGuess(entityPackage + entityName))):
                                ParameterizedTypeName.get(ClassName.bestGuess(packageName + "base.BaseEntity"),
                                        ClassName.bestGuess(entityPackage + entityName))
                        )
                        .addParameter("json",ClassName.bestGuess(String.class.getSimpleName()))
                        .addStatement("mView!!.%N(response)",funName)
                        .build());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("model.").append(funName).append("(");
        for (int i = 0; i < param.length; i++) {
            stringBuffer.append(param[i]);
            if (i != param.length - 1)
                stringBuffer.append(",");
            else
                stringBuffer.append(",%L)");

        }
        for (ParameterSpec presenterParameterSpec : presenterParameterSpecs) {
            funSpec.addParameter(presenterParameterSpec);
        }
        presenterParameterSpecs.clear();
        funSpec .addStatement(stringBuffer.toString(),compare.build())
        ;
        return funSpec.build();
    }
}
