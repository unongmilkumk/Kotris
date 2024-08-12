package kotris

import a_library.swp.SwpCanvas
import a_library.swp.SwpWindow
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

fun main() {
    val window = SwpWindow("Kotris", 1920, 1080)
    val canvas = SwpCanvas(Color.BLACK)
    val start = System.currentTimeMillis()
    val manager = KotrisManager()
    canvas.paint {
        val tick = (System.currentTimeMillis() - start) / 50
        KotrisRenderer(it, manager, tick).render()
    }

    canvas.addKKeyListener(object : KeyListener {
        override fun keyPressed(e: KeyEvent) {
            when (e.keyCode) {
                KeyEvent.VK_LEFT -> manager.movePiece(manager.x - 1, manager.y, manager.currentMino)
                KeyEvent.VK_RIGHT -> manager.movePiece(manager.x + 1, manager.y, manager.currentMino)
                KeyEvent.VK_DOWN -> manager.movePiece(manager.x, manager.y + 1, manager.currentMino)
                KeyEvent.VK_UP -> manager.clockWise(manager.x, manager.y, manager.currentMino)
                KeyEvent.VK_CONTROL -> manager.counterClockWise(manager.x, manager.y, manager.currentMino)
                KeyEvent.VK_SPACE -> manager.dropPiece()
                KeyEvent.VK_SHIFT -> manager.hold()
                KeyEvent.VK_A -> manager.halfClockWise(manager.x, manager.y, manager.currentMino)
                KeyEvent.VK_R -> {
                    manager.bag = arrayListOf<Mino>()
                    manager.next = arrayListOf<Mino>()
                    manager.hold = null
                    manager.resetPiece()
                    manager.board = hashMapOf<Pair<Int, Int>, Mino>()
                }
            }
        }

        override fun keyTyped(e: KeyEvent?) {}

        override fun keyReleased(e: KeyEvent?) {}
    })
    window.setCanvas(canvas)
    window.timerRun()
    window.enableWindow()
}