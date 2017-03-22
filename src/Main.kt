import kotlin.browser.document
import kotlin.browser.window
import kotlin.reflect.KFunction

external val React: dynamic
external val ReactDOM: dynamic

external open class ReactComponent(props: Any? = definedExternally)

fun main(args: Array<String>) {
    window.addEventListener("load", {

        val p: Props = Props(x = 10, y = 5)

        ReactDOM.render(
                el(::MyComponent1, p),
                document.getElementById("reactRoot")
        );
    });
}

fun el(tagName: String, attributes: Any? = null, vararg children: Any?) {
    return React.createElement(tagName, attributes, children)
}

fun el(f: KFunction<Any>, props: Any? = null, vararg children: Any?) {
    return React.createElement(f, props, children)
}

open class Atts(val key: String? = null, val onClick: (() -> Unit)? = null)

class Dyn(vararg val x: dynamic)

class Props(val x: Int, val y: Int, key: String? = null) : Atts(key)
data class State(val a: Int, val b: Int)

class MyComponent1 : ReactComponent {

    var props: Props
    var state: State

    constructor(props: Props) : super(props) {
        this.props = props
        this.state = State(2, 2)
    }

    fun render() {
        println(this.props.x)
        println(this.state.a)

        return el("div", null,
                el("p", Atts("11"), "state.a: " + state.a),
                el("p", Atts("22"), "props.x: " + props.x),
                el(::MyComponent2, Props(10, 5, "33")),
                el("p", Atts(key = "44") {
                    println("click")

                }, "click me"),
                div(Atts("55"),"A div")
        )
    }

    fun componentWillMount() {
        println("Foo.componentWillMount")
    }

}


fun div(atts: Any? = null, vararg childNode: Any?) {
    return React.createElement("div", atts, childNode)
}

fun MyComponent2(props: Props) {
    return el("p", null, "A functional component[" + props.x + "]")
}