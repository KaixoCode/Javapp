package javapp.core;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javapp.objects.ScrollCanvas;
import javapp.objects.text.DataDisplayer.Wrap;
import javapp.objects.text.TextArea;
import javapp.objects.text.TextField;

public class Console extends Window {

    public Console() {
        super(500, 400);
    }

    ScrollCanvas canvas;

    @Override
    public void setup() {
        getFrame().setTitle("Console");
        text = new TextField(0, 0, 400, 20);
        text.setContent("");
        text.style.font = new Font("Consolas", Font.PLAIN, 16);
        text.style.background = new Color(0, 0, 0);
        text.style.color = new Color(255, 255, 255);
        text.style.selection = new Color(255, 255, 255, 50);
        text.style.wrap = Wrap.WORD;
        text.style.padding = 1;

        canvas = new ScrollCanvas(500, 400) {

            @Override
            public void draw() {
                draw((g2d) -> {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(0, 0, getWidth(), getHeight() * 100);
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Consolas", Font.PLAIN, 16));
                    g2d.drawString(t.getClass().getName() + ".", 0, 18);
                    int w = g2d.getFontMetrics().stringWidth(t.getClass().getName() + ".");
                    text.setPosition(w, 0);
                });

                String[] split = (text.getContent() + " ").split("\\.");
                int y = 30;
                Class<?> c = t.getClass();
                Object parent = t;
                for (int i = 0; i < split.length; i++) {
                    String pa = split[i];
                    if (i == split.length - 1)
                        pa = pa.substring(0, pa.length() - 1);
                    String s = pa;
                    Field temp = null;
                    ArrayList<Method> methods = new ArrayList<Method>();
                    ArrayList<Field> fields = new ArrayList<Field>();
                    while (c != null) {
                        for (Field f : c.getDeclaredFields()) {
                            f.setAccessible(true);
                            fields.add(f);
                        }
                        for (Method m : c.getDeclaredMethods())
                            methods.add(m);
                        c = c.getSuperclass();
                    }
                    methods.removeIf((a) -> !a.getName().contains(s));
                    fields.removeIf((a) -> !a.getName().contains(s));
                    for (Field a : fields) {
                        a.setAccessible(true);

                        if (i == split.length - 1) {
                            y += 20;
                        }
                        int p = y;
                        Object t = parent;
                        int index = i;
                        draw((g2d) -> {
                            if (a.getModifiers() == 1) {
                                g2d.setColor(new Color(0, 255, 0));
                            } else {
                                g2d.setColor(new Color(0, 128, 0));
                            }
                            try {
                                String val = "";
                                if (a.getDeclaringClass().equals(t.getClass())) {
                                    val += a.get(t).toString();
                                }
                                if (index == split.length - 1) {
                                    g2d.drawString(a.getType().getName() + " " + a.getName() + " = " + val, 0, p);
                                }
                            } catch (Exception e) {
//                                e.printStackTrace();
                            }
                        });
                        if (a.getName().equals(s)) {
                            temp = a;
                        }
                    }
                    for (Method a : methods) {
                        if (i == split.length - 1) {
                            y += 20;
                        }
                        int p = y;
                        int index = i;
                        Object t = parent;
                        draw((g2d) -> {
                            if (a.getModifiers() == 1) {
                                g2d.setColor(new Color(255, 0, 0));
                            } else {
                                g2d.setColor(new Color(128, 0, 0));
                            }
                            if (a.getName().equals(s) && text.enter()) {
                                try {
                                    Object oof = a.invoke(t);
                                    System.out.println(oof.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Submitted:" + a.getName());
                            }
                            if (index == split.length - 1) {
                                g2d.drawString(a.getReturnType().getName() + " " + a.getName() + "()", 0, p);
                            }
                        });
                    }

                    if (temp != null) {
                        c = temp.getType();
                        try {
                            temp.setAccessible(true);
                            parent = temp.get(parent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
                canvas.setCanvasSize(getWidth(), y);
            }

        };
    }

    private Object t;

    public void setObject(Object t) {
        this.t = t;
    }

    private TextField text;

    @Override
    public void draw() {
        text.setSize(getWidth(), 35);
        canvas.setSize(getWidth(), getHeight());

        draw(canvas);
        draw(text);
    }

}
