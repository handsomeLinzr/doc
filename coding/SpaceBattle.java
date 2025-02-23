package coding;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SpaceBattle extends JPanel implements ActionListener, KeyListener {
    // 游戏元素尺寸
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_SIZE = 30;
    private static final int ENEMY_SIZE = 25;
    private static final int BULLET_SIZE = 5;

    // 玩家属性
    private int playerX = WIDTH/2;
    private boolean isShooting = false;

    // 子弹集合
    private ArrayList<Point> bullets = new ArrayList<>();

    // 敌人集合
    private ArrayList<Point> enemies = new ArrayList<>();
    private int enemyDirection = 1;

    // 游戏状态
    private boolean gameRunning = true;
    private int score = 0;

    public SpaceBattle() {
        // 初始化敌人
        for(int i=0; i<5; i++){
            for(int j=0; j<8; j++){
                enemies.add(new Point(100 + j*60, 50 + i*40));
            }
        }

        Timer timer = new Timer(30, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制玩家
        g.setColor(Color.BLUE);
        g.fillPolygon(new int[]{playerX, playerX+PLAYER_SIZE/2, playerX+PLAYER_SIZE},
                new int[]{HEIGHT-50, HEIGHT-80, HEIGHT-50}, 3);

        // 绘制子弹
        g.setColor(Color.RED);
        for(Point bullet : bullets) {
            g.fillRect(bullet.x, bullet.y, BULLET_SIZE, BULLET_SIZE);
        }

        // 绘制敌人
        g.setColor(Color.GREEN);
        for(Point enemy : enemies) {
            g.fillRect(enemy.x, enemy.y, ENEMY_SIZE, ENEMY_SIZE);
        }

        // 绘制分数
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameRunning) return;

        // 子弹移动
        for(int i=bullets.size()-1; i>=0; i--) {
            Point bullet = bullets.get(i);
            bullet.y -= 5;

            // 检测子弹碰撞
            for(int j=enemies.size()-1; j>=0; j--){
                Point enemy = enemies.get(j);
                if(bullet.distance(enemy.x, enemy.y) < ENEMY_SIZE) {
                    enemies.remove(j);
                    bullets.remove(i);
                    score += 10;
                    break;
                }
            }

            if(bullet.y < 0) bullets.remove(i);
        }

        // 敌人移动
        boolean changeDirection = false;
        for(Point enemy : enemies) {
            enemy.x += 2 * enemyDirection;
            if(enemy.x > WIDTH-ENEMY_SIZE || enemy.x < 0) {
                changeDirection = true;
            }
        }

        if(changeDirection) {
            enemyDirection *= -1;
            for(Point enemy : enemies) {
                enemy.y += 10;
                if(enemy.y > HEIGHT-100) gameOver();
            }
        }

        repaint();
    }

    private void gameOver() {
        gameRunning = false;
        JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
        System.exit(0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(playerX > 0) playerX -= 15;
                break;
            case KeyEvent.VK_RIGHT:
                if(playerX < WIDTH-PLAYER_SIZE) playerX += 15;
                break;
            case KeyEvent.VK_SPACE:
                bullets.add(new Point(playerX + PLAYER_SIZE/2, HEIGHT-80));
                break;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Battle");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SpaceBattle());
        frame.setVisible(true);
    }

    // 其他未使用的接口方法
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
