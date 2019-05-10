package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log

class Player: GameObject{

    override var boundingBox: Rect
    private var player: ArrayList<Bitmap>
    private var playerLocation = PointF(0f,0f)
    private var playerFrame: Int = 0
    override var collisions: Array<Boolean> = arrayOf(false, false, false, false, false) // LEFT, UP, RIGHT, DOWN
    private var isJumping = false
    private var velocityY = -5f
    private var mTime = 0f

    constructor(mBitmap: ArrayList<Bitmap>, startingPoint: PointF) {
        player = mBitmap
        playerLocation.x = startingPoint.x
        playerLocation.y = startingPoint.y
        boundingBox = Rect(playerLocation.x.toInt(), playerLocation.y.toInt(), (playerLocation.x + mBitmap[0].width).toInt(), (playerLocation.y + mBitmap[0].height).toInt())
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(player[playerFrame], playerLocation.x, playerLocation.y, null)
    }

    // Ignore input location because the player already has this information
    override fun update(userInput: UserInput, directions: Array<Boolean>) {
        when(userInput) {
            UserInput.LEFT -> {if(!collisions[0] && playerLocation.x >= 0) {
                if (isJumping) {
                    trajectory(playerLocation, 0.5f)
                }
                else {
                    playerLocation.x -= 5
                }

                if(playerFrame == 0) {
                    playerFrame = 1
                }
                else playerFrame = 0
            }}
            UserInput.RIGHT -> {if(!collisions[2] && !collisions[4]) {
                if (isJumping) {
                    trajectory(playerLocation, 0.5f)
                }
                else {
                    playerLocation.x += 5
                }
                if(playerFrame == 0) {
                    playerFrame = 1
                }
                else playerFrame = 0
            }}
            UserInput.JUMP -> { if(!isJumping) {
                isJumping = true
                velocityY = -100f
                trajectory(playerLocation, 0.5f)


            }}
            else -> {playerFrame = 0
                if (isJumping) {
                    trajectory(playerLocation, 0.5f)
                }}
        }
        Log.i("HOM", "Jump Coor: "+playerLocation.y)
        boundingBox.left = playerLocation.x.toInt()
        boundingBox.right = (playerLocation.x + player[0].width).toInt()
    }

    fun getLocation(point: PointF) {
        point.x = playerLocation.x
        point.y = playerLocation.y
    }

    override fun collision(pObj: Physics){

    }

    override fun trajectory(point: PointF, time: Float){
        // x(t) = x0 + v0 * t + 0.5g * t^2
        mTime += time
        point.x += (5f * (mTime))
        point.y -= (20f * (mTime) - 0.5f*5f*mTime*mTime)
    }
}