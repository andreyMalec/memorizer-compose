// Запуск скрипта Windows: kotlin ModuleFactory.kts <1arg> <2arg> (optional <3arg>)
// 1 аргумент - пакет, например ru.spb
// 2 аргумент - название модуля, например menu
// 3 аргумент - название features, например Menu(Menu+MenuPage+...), через '+'
// Если модуль существует, создаст только features, если такие features существуют, то перезапишит генерируемые файлы

import java.io.File

val project = if (args.isNotEmpty()) args[0] else "ru.spb"
val moduleName = if (args.isNotEmpty() && args.size > 1) args[1] else "test"
val featureNamesStr = if (args.isNotEmpty() && args.size > 2) args[2] else "test"
val listFeatureName = featureNamesStr.split("+")
val modulePackageName = "$project.$moduleName"

println("Начинаем создавать")

val moduleDir = File("feature/$moduleName")
val moduleDirPath = moduleDir.absolutePath
val dirSrcMainPath = "$moduleDirPath/src/main"

if (!moduleDir.exists()) {
    makeFileWithText(moduleDirPath, ".gitignore", "/build\n")
    makeFileWithText(moduleDirPath, "build.gradle", buildGradle())
    makeFileWithText(moduleDirPath, "consumer-rules.pro", "\n")
    makeFileWithText(moduleDirPath, "proguard-rules.pro", proguardRules())
    File("settings.gradle").appendText("include ':feature:$moduleName'\n")
    makeFileWithText(
        dirSrcMainPath,
        "AndroidManifest.xml",
        "<manifest package=\"$modulePackageName\" />\n"
    )
}
if (listFeatureName.size == 1 && listFeatureName.first() == "test") {
    val fullPackageName = "$project.$moduleName"
    val featureName = moduleName.first().uppercase() + moduleName.substring(1)
    buildFeature(featureName, fullPackageName)
} else {
    listFeatureName.forEach { featureName ->
        val fullPackageName = "$project.$moduleName.${featureName.lowercase()}"
        buildFeature(featureName, fullPackageName)
    }
}

println()
println("Создание завершено!")

fun buildFeature(featureName: String, fullPackageName: String) {
    val packages = fullPackageName.split(".").joinToString("/")
    val dirPath = "$dirSrcMainPath/java/$packages"
    makeFileWithText("$dirPath/dependencies", "${featureName}Output.kt", output(fullPackageName, featureName))
    makeFileWithText("$dirPath/di", "${featureName}StoreModule.kt", module(fullPackageName, featureName))
    makeFileWithText("$dirPath/entrypoint", "${featureName}Screen.kt", entryPoint(fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/store/action", "${featureName}Action.kt", action(fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/store/actionhandler", "BackClickActionHandler.kt", actionhandler(fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/store/reducer", "${featureName}Reducer.kt", reducer(fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/store/state", "${featureName}State.kt", state(fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/store/state", "${featureName}Event.kt", event(fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/store", "${featureName}Store.kt", store(fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/view", "${featureName}Fragment.kt", fragment(modulePackageName, fullPackageName, featureName))
    makeFileWithText("$dirPath/internal/presentation/viewcontroller", "${featureName}ViewController.kt", viewController(fullPackageName, featureName))
    val resDirPath = "$dirSrcMainPath/res"
    val xmlName = featureName.replace(Regex("""(?=[A-Z]{1}[a-z]+)""")) { "_${it.value}" }.lowercase()
    makeFileWithText("$resDirPath/layout", "fragment$xmlName.xml", fragmentXml())
}

fun fragmentXml() =
    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<androidx.constraintlayout.widget.ConstraintLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
            "    android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"match_parent\"\n" +
            "    android:focusable=\"true\"\n" +
            "    android:clickable=\"true\"\n" +
            "    android:background=\"?attr/elementsWhite\">\n" +
            "\n" +
            "    <include\n" +
            "        layout=\"@layout/appbar\"\n" +
            "        app:layout_constraintLeft_toLeftOf=\"parent\"\n" +
            "        app:layout_constraintRight_toRightOf=\"parent\"\n" +
            "        app:layout_constraintTop_toTopOf=\"parent\" />\n" +
            "\n" +
            "    <include\n" +
            "        android:id=\"@+id/inc_progress_bar\"\n" +
            "        layout=\"@layout/progress_bar\"\n" +
            "        app:layout_constraintBottom_toBottomOf=\"parent\"\n" +
            "        app:layout_constraintEnd_toEndOf=\"parent\"\n" +
            "        app:layout_constraintStart_toStartOf=\"parent\"\n" +
            "        app:layout_constraintTop_toBottomOf=\"@+id/app_bar\" />\n" +
            "\n" +
            "</androidx.constraintlayout.widget.ConstraintLayout>"

fun viewController(fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.viewcontroller\n" +
            "\n" +
            "import ru.fabit.viewcontroller.HiltViewController\n" +
            "import ru.fabit.viewcontroller.StateView\n" +
            "import ru.fabit.viewcontroller.ViewController\n" +
            "import $fullPackageName.internal.presentation.store.${className}Store\n" +
            "import $fullPackageName.internal.presentation.store.action.${className}Action\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}State\n" +
            "import javax.inject.Inject\n" +
            "\n" +
            "@HiltViewController\n" +
            "class ${className}ViewController @Inject constructor(\n" +
            "    store: ${className}Store,\n" +
            ") : ViewController<${className}State, ${className}Action, StateView<${className}State>>(store) {\n" +
            "\n" +
            "    fun onBackClicked() {\n" +
            "        dispatchAction(${className}Action.Back)\n" +
            "    }\n" +
            "\n" +
            "}"

fun fragment(modulePackageName: String, fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.view\n" +
            "\n" +
            "import android.os.Bundle\n" +
            "import android.view.LayoutInflater\n" +
            "import android.view.View\n" +
            "import android.view.ViewGroup\n" +
            "import dagger.hilt.android.AndroidEntryPoint\n" +
            "import ru.fabit.viewcontroller.StateView\n" +
            "import ru.fabit.viewcontroller.registerViewController\n" +
            "import ru.fabit.viewcontroller.viewControllers\n" +
            "import $modulePackageName.databinding.Fragment${className}Binding\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}State\n" +
            "import $fullPackageName.internal.presentation.viewcontroller.${className}ViewController\n" +
            "import $project.ui.fragment.BaseFragment\n" +
            "\n" +
            "@AndroidEntryPoint\n" +
            "class ${className}Fragment : BaseFragment<Fragment${className}Binding>(),\n" +
            "    StateView<${className}State> {\n" +
            "\n" +
            "    private val viewController: ${className}ViewController by viewControllers()\n" +
            "\n" +
            "    companion object {\n" +
            "        fun newInstance() = ${className}Fragment()\n" +
            "    }\n" +
            "\n" +
            "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
            "        super.onCreate(savedInstanceState)\n" +
            "        registerViewController(viewController)\n" +
            "    }\n" +
            "\n" +
            "    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {\n" +
            "        super.onViewCreated(view, savedInstanceState)\n" +
            "\n" +
            "        setupToolbar(\n" +
            "            inflatedFragment = view,\n" +
            "            toolbarNavigationButtonClickListener = btnBackClickListener\n" +
            "        )\n" +
            "    }\n" +
            "\n" +
            "    override fun renderState(state: ${className}State) {\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    override fun createViewBinding(\n" +
            "        inflater: LayoutInflater,\n" +
            "        parent: ViewGroup?,\n" +
            "        attachToParent: Boolean\n" +
            "    ) = Fragment${className}Binding.inflate(inflater, parent, attachToParent)\n" +
            "\n" +
            "    //region ==================== Ui Handler ====================\n" +
            "\n" +
            "    private val btnBackClickListener = View.OnClickListener { viewController.onBackClicked() }\n" +
            "\n" +
            "    override fun onBackClicked(): Boolean {\n" +
            "        viewController.onBackClicked()\n" +
            "        return true\n" +
            "    }\n" +
            "\n" +
            "    //endregion\n" +
            "\n" +
            "}"

fun store(fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.store\n" +
            "\n" +
            "import ru.fabit.storecoroutines.*\n" +
            "import $fullPackageName.internal.presentation.store.action.${className}Action\n" +
            "import $fullPackageName.internal.presentation.store.reducer.${className}Reducer\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}Event\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}State\n" +
            "\n" +
            "class ${className}Store(\n" +
            "    state: ${className}State,\n" +
            "    reducer: ${className}Reducer,\n" +
            "    errorHandler: ErrorHandler,\n" +
            "    bootstrapAction: ${className}Action,\n" +
            "    actionSources: List<ActionSource<${className}Action>>,\n" +
            "    bindActionSources: List<BindActionSource<${className}State, ${className}Action>>,\n" +
            "    sideEffects: List<SideEffect<${className}State, ${className}Action>>,\n" +
            "    actionHandlers: List<ActionHandler<${className}State, ${className}Action>>,\n" +
            "): EventsStore<${className}State, ${className}Action, ${className}Event>(\n" +
            "    startState = state,\n" +
            "    reducer = reducer,\n" +
            "    errorHandler = errorHandler,\n" +
            "    bootstrapAction = bootstrapAction,\n" +
            "    sideEffects = sideEffects,\n" +
            "    bindActionSources = bindActionSources,\n" +
            "    actionSources = actionSources,\n" +
            "    actionHandlers = actionHandlers\n" +
            ")"

fun state(fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.store.state\n" +
            "\n" +
            "import ru.fabit.storecoroutines.EventsState\n" +
            "\n" +
            "data class ${className}State(\n" +
            "    val isLoading: Boolean = false\n" +
            ") : EventsState<${className}Event>()\n"

fun event(fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.store.state\n" +
            "\n" +
            "sealed interface ${className}Event {\n" +
            "    data class Error(val message: String) : ${className}Event\n" +
            "}\n"

fun reducer(fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.store.reducer\n" +
            "\n" +
            "import ru.fabit.storecoroutines.EventsReducer\n" +
            "import $fullPackageName.internal.presentation.store.action.${className}Action\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}Event\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}State\n" +
            "\n" +
            "class ${className}Reducer : EventsReducer<${className}State, ${className}Action> {\n" +
            "    override fun reduce(\n" +
            "        state: ${className}State,\n" +
            "        action: ${className}Action\n" +
            "    ): ${className}State {\n" +
            "        return when (action) {\n" +
            "            is ${className}Action.Error -> state.copy().apply {\n" +
            "                action.error.message?.let {\n" +
            "                    addEvent(${className}Event.Error(it))\n" +
            "                }\n" +
            "            }\n" +
            "            else -> state.copy()\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    override fun copy(state: ${className}State) = state.copy()\n" +
            "}"

fun actionhandler(fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.store.actionhandler\n" +
            "\n" +
            "import ru.fabit.storecoroutines.ActionHandler\n" +
            "import $fullPackageName.dependencies.${className}Output\n" +
            "import $fullPackageName.internal.presentation.store.action.${className}Action\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}State\n" +
            "import javax.inject.Inject\n" +
            "\n" +
            "class BackClickActionHandler @Inject constructor(\n" +
            "    output: ${className}Output\n" +
            ") : ActionHandler<${className}State, ${className}Action>(\n" +
            "    requirement = { action -> action is ${className}Action.Back },\n" +
            "    handler = { _, _ -> output.back() }\n" +
            ")"

fun action(fullPackageName: String, className: String) =
    "package $fullPackageName.internal.presentation.store.action\n" +
            "\n" +
            "sealed interface ${className}Action {\n" +
            "    object Init : ${className}Action\n" +
            "    object Back : ${className}Action\n" +
            "    data class Error(val error: Throwable) : ${className}Action\n" +
            "}\n"

fun entryPoint(fullPackageName: String, className: String) =
    "package $fullPackageName.entrypoint\n" +
            "\n" +
            "import androidx.fragment.app.Fragment\n" +
            "import androidx.fragment.app.FragmentFactory\n" +
            "import ru.spb.ui.screen.OverlayFragmentScreen\n" +
            "import $fullPackageName.internal.presentation.view.${className}Fragment\n" +
            "\n" +
            "object ${className}Screen : OverlayFragmentScreen {\n" +
            "    override fun createFragment(factory: FragmentFactory): Fragment {\n" +
            "        return ${className}Fragment.newInstance()\n" +
            "    }\n" +
            "}"

fun module(fullPackageName: String, className: String) =
    "package $fullPackageName.di\n" +
            "\n" +
            "import dagger.Module\n" +
            "import dagger.Provides\n" +
            "import dagger.hilt.InstallIn\n" +
            "import ru.fabit.storecoroutines.ErrorHandler\n" +
            "import ru.fabit.viewcontroller.ViewControllerComponent\n" +
            "import ru.fabit.viewcontroller.ViewControllerScoped\n" +
            "import $fullPackageName.internal.presentation.store.${className}Store\n" +
            "import $fullPackageName.internal.presentation.store.action.${className}Action\n" +
            "import $fullPackageName.internal.presentation.store.actionhandler.BackClickActionHandler\n" +
            "import $fullPackageName.internal.presentation.store.reducer.${className}Reducer\n" +
            "import $fullPackageName.internal.presentation.store.state.${className}State\n" +
            "\n" +
            "@Module\n" +
            "@InstallIn(ViewControllerComponent::class)\n" +
            "class ${className}StoreModule {\n" +
            "\n" +
            "    @Provides\n" +
            "    @ViewControllerScoped\n" +
            "    fun provideStore(\n" +
            "        backClickActionHandler: BackClickActionHandler,\n" +
            "        errorHandler: ErrorHandler,\n" +
            "    ): ${className}Store {\n" +
            "        return ${className}Store(\n" +
            "            state = ${className}State(),\n" +
            "            reducer = ${className}Reducer(),\n" +
            "            errorHandler = errorHandler,\n" +
            "            bootstrapAction = ${className}Action.Init,\n" +
            "            actionSources = listOf(),\n" +
            "            bindActionSources = listOf(),\n" +
            "            sideEffects = listOf(),\n" +
            "            actionHandlers = listOf(backClickActionHandler)\n" +
            "        )\n" +
            "    }\n" +
            "}"

fun output(fullPackageName: String, className: String) =
    "package $fullPackageName.dependencies\n" +
            "\n" +
            "interface ${className}Output {\n" +
            "    fun back()\n" +
            "}"

fun proguardRules() =
    "# Add project specific ProGuard rules here.\n" +
            "# You can control the set of applied configuration files using the\n" +
            "# proguardFiles setting in build.gradle.\n" +
            "#\n" +
            "# For more details, see\n" +
            "#   http://developer.android.com/guide/developing/tools/proguard.html\n" +
            "\n" +
            "# If your project uses WebView with JS, uncomment the following\n" +
            "# and specify the fully qualified class name to the JavaScript interface\n" +
            "# class:\n" +
            "#-keepclassmembers class fqcn.of.javascript.interface.for.webview {\n" +
            "#   public *;\n" +
            "#}\n" +
            "\n" +
            "# Uncomment this to preserve the line number information for\n" +
            "# debugging stack traces.\n" +
            "#-keepattributes SourceFile,LineNumberTable\n" +
            "\n" +
            "# If you keep the line number information, uncomment this to\n" +
            "# hide the original source file name.\n" +
            "#-renamesourcefileattribute SourceFile\n"

fun buildGradle() =
    "apply plugin: 'com.android.library'\n" +
            "apply plugin: 'kotlin-android'\n" +
            "apply plugin: 'kotlin-kapt'\n" +
            "apply plugin: 'dagger.hilt.android.plugin'\n" +
            "\n" +
            "android {\n" +
            "    def config = rootProject.ext\n" +
            "    compileSdkVersion config.androidCompileSdkVersion\n" +
            "    buildToolsVersion config.androidBuildToolsVersion\n" +
            "\n" +
            "    defaultConfig {\n" +
            "        minSdkVersion config.androidMinSdkVersion\n" +
            "        targetSdkVersion config.androidTargetSdkVersion\n" +
            "        vectorDrawables.useSupportLibrary = true\n" +
            "\n" +
            "        testInstrumentationRunner \"androidx.test.runner.AndroidJUnitRunner\"\n" +
            "        consumerProguardFiles 'consumer-rules.pro'\n" +
            "    }\n" +
            "\n" +
            "    buildTypes {\n" +
            "        release {\n" +
            "            minifyEnabled true\n" +
            "            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
            "        }\n" +
            "        debug {\n" +
            "            minifyEnabled true\n" +
            "            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    compileOptions {\n" +
            "        sourceCompatibility JavaVersion.VERSION_1_8\n" +
            "        targetCompatibility JavaVersion.VERSION_1_8\n" +
            "    }\n" +
            "\n" +
            "}\n" +
            "\n" +
            "dependencies {\n" +
            "    def deps = rootProject.ext.featureDependencies\n" +
            "\n" +
            "    implementation project(path: ':core:ui')\n" +
            "    implementation project(path: ':core:domain')\n" +
            "    implementation project(path: ':core:presentation')\n" +
            "\n" +
            "    implementation deps.hiltAndroid\n" +
            "    kapt deps.hiltCompiler\n" +
            "\n" +
            "    implementation deps.cicerone\n" +
            "    implementation deps.store\n" +
            "    implementation deps.viewController\n" +
            "    implementation deps.interactor\n" +
            "\n" +
            "    implementation deps.kotlinStdlib\n" +
            "    implementation deps.coroutines\n" +
            "    implementation deps.coreKtx\n" +
            "    implementation deps.appcompat\n" +
            "    implementation deps.material\n" +
            "}\n"

fun makeFileWithText(path: String, fileName: String, text: String) {
    println("    $path/$fileName")
    File(path).mkdirs()
    val file = File("$path/$fileName")
    file.createNewFile()
    file.writer().apply {
        append(text)
        flush()
        close()
    }
}
