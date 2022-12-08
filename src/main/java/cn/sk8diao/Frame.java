package cn.sk8diao;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * 作者: 刁旺睿
 * 时间: 2022/12/6 14:45
 */
public class Frame extends JFrame {

    private final String[] columnNames = {"头像", "名字", "被动技能", "定位", "胜率"};
    private Object[][] rowData = {
            {new ImageIcon("src/images/Kayle.png"), "正义天使 凯尔", "登神长阶", "战士 辅助", "47.42%"},
            {new ImageIcon("src/images/Veigar.png"), "邪恶小法师 维迦", "超凡邪力", "法师", "48.07%"},
            {new ImageIcon("src/images/Seraphine.png"), "星籁歌姬 萨勒芬妮", "星光漫射", "法师 辅助", "48.93%"},
            {new ImageIcon("src/images/Draven.png"), "荣耀行刑官 德莱文", "德莱文联盟", "射手", "48.97%"},
            {new ImageIcon("src/images/Jayce.png"), "未来守护者 杰斯", "海克斯科技电容", "战士 射手", "48.3%"},
            {new ImageIcon("src/images/Ziggs.png"), "爆破鬼才 吉格斯", "一触即发", "法师", "48.97%"},
            {new ImageIcon("src/images/Lulu.png"), "仙灵女巫 璐璐", "皮克斯，仙灵伙伴", "辅助 法师", "48.99%"},
            {new ImageIcon("src/images/Pantheon.png"), "不屈之枪 潘森", "矢志不退", "战士 刺客", "46.74%"},
            {new ImageIcon("src/images/Jax.png"), "武器大师 贾克斯", "无情连打", "战士 刺客", "47.95%"},
            {new ImageIcon("src/images/Shaco.png"), "恶魔小丑 萨科", "背刺", "刺客", "47%"}
    };
    private DefaultTableModel defaultTableModel;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private JLabel jLabel;
    private JPanel jPanel;
    private JSplitPane jSplitPane;
    private JPopupMenu jPopupMenu;
    private JMenuItem jMenuItem;

    public Frame() {

        //初始化图形界面
        super("JTable By 刁旺睿");//设置窗口标题
        setSize(800, 600);//设置宽高
        setLocationRelativeTo(null);//设置居中
        setDefaultCloseOperation(EXIT_ON_CLOSE);//设置关闭
        setResizable(false);//设置窗体不可改变大小
        setLayout(new FlowLayout());//设置布局

        //创建DefaultTableModel
        defaultTableModel = new DefaultTableModel(rowData, columnNames);

        //创建JTable
        jTable = new JTable() {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };//重写getColumnClass方法来显示图片
        jTable.setModel(defaultTableModel);
        jTable.getTableHeader().setFont(new Font("黑体", Font.BOLD, 20));//设置表头字体
        jTable.setFont(new Font("黑体", Font.PLAIN, 15));//设置表格字体
        jTable.setRowHeight(60);//设置表格行宽
        jTable.getColumnModel().getColumn(0).setMaxWidth(60);//设置第一列宽度
        jTable.getColumnModel().getColumn(0).setMinWidth(60);//设置第一列宽度
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//在所有的调整大小操作中，按比例调整所有的列。
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();//单元格渲染器
        defaultTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);//居中显示
        jTable.setDefaultRenderer(Object.class, defaultTableCellRenderer);//设置渲染器
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                    jLabel.setText("<html><body><div align=\"center\">" + "选中的单元格: " + jTable.getValueAt(jTable.getSelectedRow(), jTable.getSelectedColumn()) + "</div><div align=\"center\">" + "选中的行: " + jTable.getValueAt(jTable.getSelectedRow(), 1) + " " + jTable.getValueAt(jTable.getSelectedRow(), 2) + " " + jTable.getValueAt(jTable.getSelectedRow(), 3) + " " + jTable.getValueAt(jTable.getSelectedRow(), 4) + " " + "</div></body></html>");
                }
                if (e.getButton() == 3) {
                    if (jTable.getSelectedRow() == -1) {
                        jLabel.setText("请选中行再点击右键");
                    } else {
                        jPopupMenu.show(jTable, e.getX(), e.getY());
                    }
                }
            }
        });//添加选中事件监听器

        //设置选择模式
        ListSelectionModel selectionModel = jTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //创建JScrollPane并将JTable放入其中
        jScrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //创建JLabel
        jLabel = new JLabel();
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        jLabel.setFont(new Font("黑体", Font.BOLD, 20));

        //创建JPane
        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jLabel, BorderLayout.CENTER);//添加创建好的JLabel

        //创建JPopupMenu
        jPopupMenu = new JPopupMenu();

        //创建JMenuItem并添加到JPopupMenu
        jMenuItem = new JMenuItem("在该行的下方放一只猫咪");
        jMenuItem.setFont(new Font("黑体", Font.BOLD, 15));
        jMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("在" + jTable.getSelectedRow() + "行的下方放一只猫咪");
                defaultTableModel.insertRow(jTable.getSelectedRow() + 1, new Object[]{
                        new ImageIcon("src/images/Yuumi.png"),
                        "魔法猫咪 悠米",
                        "连打带挡",
                        "辅助 法师",
                        "50.51%"
                });
            }
        });
        jPopupMenu.add(jMenuItem);

        //创建分割面板
        jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jScrollPane, jPanel);
        jSplitPane.setEnabled(false);//设置分割线不可移动
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                jSplitPane.setDividerLocation(0.7);
            }
        });//设置监听器 获取当前窗口大小 按比例分配

        setContentPane(jSplitPane);
        setVisible(true);
    }

}