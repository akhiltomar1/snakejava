import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    static final int Screenwidth = 600;
    static final int Screenheight = 600;
    static final int unitsize = 25;
    static final int gameunits = (Screenheight*Screenwidth)/unitsize;
    static final int delay = 75;
    final int x[] = new int[gameunits];
    final int y[] = new int[gameunits];
    int body = 6;
    int appleseaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running  = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(Screenwidth,Screenheight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startgame();
    }
    public void startgame(){
        newApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            /*
            for(int i = 0;i<Screenheight/unitsize;i++){
                g.drawLine(i*unitsize, 0, i*unitsize, Screenheight);
                g.drawLine( 0,i*unitsize,  Screenwidth,i*unitsize);
            }*/
                g.setColor(Color.red);
                g.fillOval(appleX,appleY,unitsize,unitsize);
                for(int i = 0; i < body; i++){
                    if( i == 0 ){
                        g.setColor(Color.green);
                        g.fillRect(x[i],y[i],unitsize,unitsize);
                    }
                    else{
                        g.setColor(Color.yellow);
                        g.fillRect(x[i],y[i],unitsize,unitsize);
                     }
                }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD, 25));
            g.drawString("Score:" + appleseaten,10,20);
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(Screenwidth/unitsize))*unitsize;
        appleY = random.nextInt((int)(Screenheight/unitsize))*unitsize;

    }
    public void move(){
        for(int i  = body; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - unitsize;
                break;
            case 'D':
                y[0] = y[0] + unitsize;
                break;
            case 'L':
                x[0] = x[0] - unitsize;
                break;
            case 'R':
                x[0] = x[0] + unitsize;
                break;
        }

    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            body++;
            appleseaten++;
            newApple();
        }
    }
    public void checkCollision(){
        //checks if head collides with body
        for(int i = body; i > 0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //checks if head collides with left border
        if(x[0] <0){
            running = false;
         }
        //checks if head collides with right border
        if(x[0] > Screenwidth){
            running = false;
        }
        //checks if head collides with top border
        if(y[0] < 0){
            running = false;
        }
        //checks if head collides with bottom border
        if(y[0] > Screenheight){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        g.drawString("GAME OVER",90,300);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 25));
        g.drawString("Score:" + appleseaten,10,20);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent A){
            switch (A.getKeyCode()){

                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }

        }


    }
}
