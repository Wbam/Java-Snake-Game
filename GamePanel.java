import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int screenWidth=600;
    static final int screenHeight=600;
    static final int unitSize=25;
    static final int gameUnits=(screenHeight*screenWidth)/unitSize;
    static final int delay=75;
    final int x[]=new int[gameUnits];
    final int y[]=new int[gameUnits];
    int bodyParts=6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction='r';
    boolean running=false;
    Timer timer;
    Random random;
    public GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdaptor());
        startGame();

    }
    public void startGame(){
               newApple();
               running=true;
               timer=new Timer(delay,this);
               timer.start();
    }
   public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running){
//        for(int i=0;i<screenHeight/unitSize;i++){
//            g.drawLine(i*unitSize,0,i*unitSize,screenHeight);
//            g.drawLine(0,i*unitSize,screenWidth,i*unitSize);
//        }
        g.setColor(Color.red);
        g.fillOval(appleX,appleY,unitSize,unitSize);

        for (int i=0;i<bodyParts;i++){
            if (i==0){
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],unitSize,unitSize);
            }
            else {
                g.setColor(new Color(45,180,0));
//                g.setColor(new Color(random.nextInt(255),
//                        random.nextInt(255),
//                        random.nextInt(255)));
                g.fillRect(x[i],y[i],unitSize,unitSize);
            }
        }
            g.setColor(Color.green);
            g.setFont(new Font("Ink Free",Font.BOLD,25));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,
                    (screenWidth-metrics.stringWidth("Scores: "+applesEaten))/2,
                        g.getFont().getSize());
        }
        else
            gameOver(g);

    }
    public void newApple(){
        appleX=random.nextInt((int)(screenWidth/unitSize))*unitSize;
        appleY=random.nextInt((int)(screenHeight/unitSize))*unitSize;

    }
    public void move(){
        for (int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'u':
                y[0]=y[0]-unitSize;
                break;
            case 'd':
                y[0]=y[0]+unitSize;
                break;
            case 'l':
                x[0]=x[0]-unitSize;
                break;
            case 'r':
                x[0]=x[0]+unitSize;
                break;
        }

    }
    public void checkApple(){
        if ((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollision(){
        for(int i=bodyParts;i>0;i--){
            if ((x[0]==x[i])&&(y[0]==y[i]))
                running=false;
        }
        if(x[0]<0)
            running=false;
        if (x[0]>screenWidth)
            running=false;
        if (y[0]<0)
            running=false;
        if(y[0]>screenHeight)
            running=false;
        if(!running)
            timer.stop();
    }
    public void gameOver(Graphics g){
        g.setColor(Color.green);
        g.setFont(new Font("Ink Free",Font.BOLD,25));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(screenWidth-metrics1.stringWidth("Scores: "+applesEaten))/2,g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over",(screenWidth-metrics.stringWidth("Game Over"))/2,screenHeight/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }
    public class myKeyAdaptor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='r') direction='l';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='l') direction='r';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='u') direction='d';
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='d') direction='u';
                    break;

            }

        }
    }
}
