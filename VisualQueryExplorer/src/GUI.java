
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author SCOCRI
 */
public class GUI extends javax.swing.JFrame {

	private final String path = "C:/Sviluppo/PagoWEB/pago/exe";
	private VQList v;
	
	JPopupMenu menu;

	/**
	 * Creates new form GUI
	 */
	public GUI() {
		initComponents();

		//Mostro tutte le VisualQuery
		v = new VQList(path);
		v.generateList();

		DefaultTableModel model = (DefaultTableModel) VisualQueryList.getModel();
		for (int i = 0; i < v.getList().length; i++) {
			model.insertRow(i, new Object[]{v.getList()[i].getName()});
		}

		//Menu per il copia verso SitePainter
		menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("Copy (SitePainter SELECT definition)");

		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection ss = new StringSelection(getReadSP(getSelectedVQ()));
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			}
		});
		menu.add(item);

		//Aggiungio l'evento per la selezione con il mouse
		VisualQueryList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					launchVQInBrowser(getSelectedVQ());
				} else if (!e.isPopupTrigger()) {
					ShowVisualQuery(path + "/" + getSelectedVQ());
				}
			}

			@Override
			public void mouseReleased(MouseEvent ev) {
				if (ev.isPopupTrigger()) {
					menu.show(ev.getComponent(), ev.getX(), ev.getY());
				}
			}
		});

		//Aggiungio l'evento per la selezione con la tastiera
		VisualQueryList.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					ShowVisualQuery(path + "/" + getSelectedVQ());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					ShowVisualQuery(path + "/" + getSelectedVQ());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		try {
			Image icon = ImageIO.read(this.getClass().getResource("Goku.png"));
			this.setIconImage(icon);
		} catch (IOException ex) {
		}

		//Centro il frame nello schermo
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = this.getSize();
		screenSize.height = screenSize.height / 2;
		screenSize.width = screenSize.width / 2;
		size.height = size.height / 2;
		size.width = size.width / 2;
		int y = screenSize.height - size.height;
		int x = screenSize.width - size.width;
		this.setLocation(x, y);
	}	
	
	private String getSelectedVQ(){
		int nRow = VisualQueryList.getSelectedRow();
		int nCol = VisualQueryList.getSelectedColumn();

		//get selected object
		Object objSel = VisualQueryList.getValueAt(nRow, nCol);

		return objSel.toString();
	}
	
	private String getReadSP(String visQue) {
		String ClipBoardTxt = "";

		VisualQuery VQ = new VisualQuery();
		VQ.composeVQ(path + "/" + visQue);

		ClipBoardTxt += "* --- CodePainter Revolution Batch Cut Vers. 1.0" + "\n"
				+ "[" + "\n"
				+ "Icpitembatch.CPSelect" + "\n"
				+ "h=[" + "\n"
				+ "19" + "\n"
				+ "19" + "\n"
				+ "]" + "\n"
				+ "obj='Select" + "\n"
				+ "pag=1" + "\n"
				+ "par_expr=[" + "\n";

		VQFilterParameter[] f = VQ.getFilterParameter();

		for (int i = 0; i < f.length; i++) {
			ClipBoardTxt += "'" + "\n";
		}

		ClipBoardTxt += "]" + "\n"
				+ "par_name=[" + "\n";

		for (int i = 0; i < f.length; i++) {
			ClipBoardTxt += "'" + f[i].getFieldName() + "\n";
		}

		ClipBoardTxt += "]" + "\n"
				+ "sel_name='" + visQue.substring(0, visQue.indexOf(".")) + "\n"
				+ "sel_type='sqry" + "\n"
				+ "sons=[" + "\n"
				+ "Icpitembatch.CPItemSon" + "\n"
				+ "I" + "\n"
				+ "]" + "\n"
				+ "w=[" + "\n"
				+ "150" + "\n"
				+ "150" + "\n"
				+ "]" + "\n"
				+ "I" + "\n"
				+ "]" + "\n"
				+ "*--*" + "\n";

		return ClipBoardTxt;
	}

	private void launchVQInBrowser(String visQue) {
		String url = "http://localhost:8080/PagoWEB/visualquery/index.jsp?filename=" + visQue;
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();

		try {
			if (os.indexOf("win") >= 0) {

				// this doesn't support showing urls in the form of "page.html#nameLink"
				rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

			} else if (os.indexOf("mac") >= 0) {

				rt.exec("open " + url);

			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

				// Do a best guess on unix until we get a platform independent way
				// Build a list of browsers to try, in this order.
				String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
					"netscape", "opera", "links", "lynx"};

				// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
				StringBuilder cmd = new StringBuilder();
				for (int i = 0; i < browsers.length; i++) {
					cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
				}

				rt.exec(new String[]{"sh", "-c", cmd.toString()});
			}
		} catch (Exception ex) {
		}
	}

	private void ShowVisualQuery(String path) {
		DefaultTableModel model;

		VisualQuery VQ = new VisualQuery();
		VQ.composeVQ(path);

		VQTable[] table = VQ.getTable();
		((DefaultTableModel) VQJTable.getModel()).setRowCount(0);
		model = (DefaultTableModel) VQJTable.getModel();
		for (int i = 0; i < table.length; i++) {
			model.insertRow(i, new Object[]{table[i].getDescription(), table[i].getAlias(), table[i].getTableName(), table[i].getTempTable()});
		}

		VQField[] field = VQ.getField();
		((DefaultTableModel) VQJField.getModel()).setRowCount(0);
		model = (DefaultTableModel) VQJField.getModel();
		for (int i = 0; i < field.length; i++) {
			model.insertRow(i, new Object[]{field[i].getDescription(), field[i].getName(), field[i].getAlias(), field[i].getType(), field[i].getLen(), field[i].getDec()});
		}

		VQJoin[] join = VQ.getJoin();
		((DefaultTableModel) VQJJoin.getModel()).setRowCount(0);
		model = (DefaultTableModel) VQJJoin.getModel();
		for (int i = 0; i < join.length; i++) {
			model.insertRow(i, new Object[]{join[i].getDescription(), join[i].getExpression(), join[i].getType(), join[i].getTable1(), join[i].getTable2()});
		}

		VQFilter[] filter = VQ.getFilter();
		((DefaultTableModel) VQJFilter.getModel()).setRowCount(0);
		model = (DefaultTableModel) VQJFilter.getModel();
		for (int i = 0; i < filter.length; i++) {
			model.insertRow(i, new Object[]{filter[i].getFieldName(), filter[i].getNot(), filter[i].getCriteria(), filter[i].getExample(), filter[i].getOperator(), filter[i].getHaving()});
		}

		VQFilterParameter[] filterParameter = VQ.getFilterParameter();
		((DefaultTableModel) VQJFilterParameter.getModel()).setRowCount(0);
		model = (DefaultTableModel) VQJFilterParameter.getModel();
		for (int i = 0; i < filterParameter.length; i++) {
			model.insertRow(i, new Object[]{filterParameter[i].getFieldName(), filterParameter[i].getDescription(), filterParameter[i].getType(), filterParameter[i].getLen(), filterParameter[i].getDec(), filterParameter[i].getRoE()});
		}
	}

	private void doSearch() {
		String pattern = tfSearch.getText();
		File[] searchList = v.getList();
		ArrayList<File> found = new ArrayList<>();

		nextVQ:
		for (int i = 0; i < searchList.length; i++) {
			if (ckVisualQuery.isSelected() && searchList[i].getName().contains(pattern)) {
				found.add(searchList[i]);
				continue;
			}

			if (ckTable.isSelected()
					|| ckField.isSelected() || ckJoin.isSelected()
					|| ckFilter.isSelected() || ckFilterParameter.isSelected()
					|| ckOrderBy.isSelected() || ckGroupBy.isSelected()) {

				VisualQuery VQ = new VisualQuery();
				VQ.composeVQ(searchList[i].getAbsolutePath());

				if (ckTable.isSelected()) {
					VQTable[] t = VQ.getTable();

					for (int j = 0; j < t.length; j++) {
						if (t[j].getDescription().contains(pattern)
								|| t[j].getAlias().contains(pattern)
								|| t[j].getTableName().contains(pattern)
								|| t[j].getTempTable().contains(pattern)) {
							found.add(searchList[i]);
							continue nextVQ;
						}
					}
				}

				if (ckField.isSelected()) {
					VQField[] f = VQ.getField();

					for (int j = 0; j < f.length; j++) {
						if (f[j].getDescription().contains(pattern)
								|| f[j].getName().contains(pattern)
								|| f[j].getAlias().contains(pattern)
								|| f[j].getType().contains(pattern)
								|| f[j].getLen().contains(pattern)
								|| f[j].getDec().contains(pattern)) {
							found.add(searchList[i]);
							continue nextVQ;
						}
					}
				}

				if (ckJoin.isSelected()) {
					VQJoin[] jo = VQ.getJoin();

					for (int j = 0; j < jo.length; j++) {
						if (jo[j].getDescription().contains(pattern)
								|| jo[j].getExpression().contains(pattern)
								|| jo[j].getType().contains(pattern)
								|| jo[j].getTable1().contains(pattern)
								|| jo[j].getTable2().contains(pattern)) {
							found.add(searchList[i]);
							continue nextVQ;
						}
					}
				}

				if (ckFilter.isSelected()) {
					VQFilter[] f = VQ.getFilter();

					for (int j = 0; j < f.length; j++) {
						if (f[j].getFieldName().contains(pattern)
								|| f[j].getNot().contains(pattern)
								|| f[j].getCriteria().contains(pattern)
								|| f[j].getExample().contains(pattern)
								|| f[j].getOperator().contains(pattern)
								|| f[j].getHaving().contains(pattern)) {
							found.add(searchList[i]);
							continue nextVQ;
						}
					}
				}

				if (ckFilterParameter.isSelected()) {
					VQFilterParameter[] fp = VQ.getFilterParameter();

					for (int j = 0; j < fp.length; j++) {
						if (fp[j].getFieldName().contains(pattern)
								|| fp[j].getDescription().contains(pattern)
								|| fp[j].getType().contains(pattern)
								|| fp[j].getLen().contains(pattern)
								|| fp[j].getDec().contains(pattern)
								|| fp[j].getRoE().contains(pattern)) {
							found.add(searchList[i]);
							continue nextVQ;
						}
					}
				}

				if (ckOrderBy.isSelected()) {
					VQOrderBy[] ob = VQ.getOrderBy();

					for (int j = 0; j < ob.length; j++) {
						if (ob[j].getDescription().contains(pattern)
								|| ob[j].getFieldName().contains(pattern)
								|| ob[j].getCardinality().contains(pattern)) {
							found.add(searchList[i]);
							continue nextVQ;
						}
					}
				}

				if (ckGroupBy.isSelected()) {
					VQGroupBy[] gb = VQ.getGroupBy();

					for (int j = 0; j < gb.length; j++) {
						if (gb[j].getDescription().contains(pattern)
								|| gb[j].getFieldName().contains(pattern)) {
							found.add(searchList[i]);
							continue nextVQ;
						}
					}
				}
			}
		}

		//Svuoto tutte le tabelle
		((DefaultTableModel) VisualQueryList.getModel()).setRowCount(0);
		((DefaultTableModel) VQJTable.getModel()).setRowCount(0);
		((DefaultTableModel) VQJField.getModel()).setRowCount(0);
		((DefaultTableModel) VQJJoin.getModel()).setRowCount(0);
		((DefaultTableModel) VQJFilter.getModel()).setRowCount(0);
		((DefaultTableModel) VQJFilterParameter.getModel()).setRowCount(0);
		
		//Rigenero la lista delle Visual Query trovate 
		DefaultTableModel model = (DefaultTableModel) VisualQueryList.getModel();
		for (int i = 0; i < found.size(); i++) {
			model.insertRow(i, new Object[]{found.get(i).getName()});
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spVisualQueryList = new javax.swing.JScrollPane();
        VisualQueryList = new javax.swing.JTable();
        spField = new javax.swing.JScrollPane();
        VQJField = new javax.swing.JTable();
        spTable = new javax.swing.JScrollPane();
        VQJTable = new javax.swing.JTable();
        spFilterParameter = new javax.swing.JScrollPane();
        VQJFilterParameter = new javax.swing.JTable();
        spJoin = new javax.swing.JScrollPane();
        VQJJoin = new javax.swing.JTable();
        spFilter = new javax.swing.JScrollPane();
        VQJFilter = new javax.swing.JTable();
        tfSearch = new javax.swing.JTextField();
        bSearch = new javax.swing.JButton();
        pScope = new javax.swing.JPanel();
        ckVisualQuery = new javax.swing.JCheckBox();
        ckTable = new javax.swing.JCheckBox();
        ckJoin = new javax.swing.JCheckBox();
        ckFilter = new javax.swing.JCheckBox();
        ckFilterParameter = new javax.swing.JCheckBox();
        ckOrderBy = new javax.swing.JCheckBox();
        ckGroupBy = new javax.swing.JCheckBox();
        ckField = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Visual Query Explorer");
        setResizable(false);

        VisualQueryList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Visual Query"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VisualQueryList.setToolTipText("Visual Query");
        VisualQueryList.setColumnSelectionAllowed(true);
        VisualQueryList.setIntercellSpacing(new java.awt.Dimension(3, 3));
        VisualQueryList.getTableHeader().setReorderingAllowed(false);
        spVisualQueryList.setViewportView(VisualQueryList);
        VisualQueryList.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        VQJField.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Name", "Alias", "Type", "Len", "Dec"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VQJField.setToolTipText("Field");
        VQJField.setIntercellSpacing(new java.awt.Dimension(3, 3));
        VQJField.getTableHeader().setReorderingAllowed(false);
        spField.setViewportView(VQJField);
        VQJField.getColumnModel().getColumn(3).setPreferredWidth(5);
        VQJField.getColumnModel().getColumn(4).setPreferredWidth(5);
        VQJField.getColumnModel().getColumn(5).setPreferredWidth(5);

        VQJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Alias", "Table Name", "Temp Table"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VQJTable.setToolTipText("Table");
        VQJTable.setIntercellSpacing(new java.awt.Dimension(3, 3));
        VQJTable.getTableHeader().setReorderingAllowed(false);
        spTable.setViewportView(VQJTable);

        VQJFilterParameter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Field Name", "Description", "Type", "Len", "Dec", "RoE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VQJFilterParameter.setToolTipText("Filter Parameter");
        VQJFilterParameter.setIntercellSpacing(new java.awt.Dimension(3, 3));
        VQJFilterParameter.getTableHeader().setReorderingAllowed(false);
        spFilterParameter.setViewportView(VQJFilterParameter);
        VQJFilterParameter.getColumnModel().getColumn(2).setPreferredWidth(5);
        VQJFilterParameter.getColumnModel().getColumn(3).setPreferredWidth(5);
        VQJFilterParameter.getColumnModel().getColumn(4).setPreferredWidth(5);
        VQJFilterParameter.getColumnModel().getColumn(5).setPreferredWidth(5);

        VQJJoin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Expression", "Type", "Table1", "Table2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VQJJoin.setToolTipText("Join");
        VQJJoin.setIntercellSpacing(new java.awt.Dimension(3, 3));
        VQJJoin.getTableHeader().setReorderingAllowed(false);
        spJoin.setViewportView(VQJJoin);

        VQJFilter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Field name", "Not", "Criteria", "Example", "Operator", "Having"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VQJFilter.setToolTipText("Filter");
        VQJFilter.setIntercellSpacing(new java.awt.Dimension(3, 3));
        VQJFilter.getTableHeader().setReorderingAllowed(false);
        spFilter.setViewportView(VQJFilter);

        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSearchKeyTyped(evt);
            }
        });

        bSearch.setText("Search");
        bSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSearchActionPerformed(evt);
            }
        });

        pScope.setBorder(javax.swing.BorderFactory.createTitledBorder("Scope"));

        ckVisualQuery.setSelected(true);
        ckVisualQuery.setText("VisualQuery");

        ckTable.setText("Table");

        ckJoin.setText("Join");

        ckFilter.setText("Filter");

        ckFilterParameter.setText("Filter Parameter");

        ckOrderBy.setText("Order By");
        ckOrderBy.setEnabled(false);

        ckGroupBy.setText("Group By");
        ckGroupBy.setEnabled(false);

        ckField.setText("Field");

        javax.swing.GroupLayout pScopeLayout = new javax.swing.GroupLayout(pScope);
        pScope.setLayout(pScopeLayout);
        pScopeLayout.setHorizontalGroup(
            pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pScopeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckVisualQuery)
                    .addComponent(ckFilter)
                    .addComponent(ckOrderBy)
                    .addComponent(ckField))
                .addGap(18, 18, 18)
                .addGroup(pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckGroupBy)
                    .addComponent(ckJoin)
                    .addComponent(ckFilterParameter)
                    .addComponent(ckTable))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pScopeLayout.setVerticalGroup(
            pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pScopeLayout.createSequentialGroup()
                .addGroup(pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckVisualQuery)
                    .addComponent(ckTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckJoin)
                    .addComponent(ckField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckFilter)
                    .addComponent(ckFilterParameter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pScopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckOrderBy)
                    .addComponent(ckGroupBy))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spVisualQueryList, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSearch))
                    .addComponent(pScope, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(spField)
                    .addComponent(spJoin)
                    .addComponent(spFilter)
                    .addComponent(spFilterParameter, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spFilterParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bSearch))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pScope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spVisualQueryList, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void bSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSearchActionPerformed
		//Imposto il cursore in stato WAIT
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		doSearch();

		//Reimposto il cursore di default
		this.setCursor(Cursor.getDefaultCursor());
	}//GEN-LAST:event_bSearchActionPerformed

	private void tfSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyTyped
		if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
			//Imposto il cursore in stato WAIT
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			doSearch();

			//Reimposto il cursore di default
			this.setCursor(Cursor.getDefaultCursor());
		}
	}//GEN-LAST:event_tfSearchKeyTyped

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
		 * If Windows Look And Feel is not available, stay with the default look
		 * and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
		}
		//</editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new GUI().setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable VQJField;
    private javax.swing.JTable VQJFilter;
    private javax.swing.JTable VQJFilterParameter;
    private javax.swing.JTable VQJJoin;
    private javax.swing.JTable VQJTable;
    private javax.swing.JTable VisualQueryList;
    private javax.swing.JButton bSearch;
    private javax.swing.JCheckBox ckField;
    private javax.swing.JCheckBox ckFilter;
    private javax.swing.JCheckBox ckFilterParameter;
    private javax.swing.JCheckBox ckGroupBy;
    private javax.swing.JCheckBox ckJoin;
    private javax.swing.JCheckBox ckOrderBy;
    private javax.swing.JCheckBox ckTable;
    private javax.swing.JCheckBox ckVisualQuery;
    private javax.swing.JPanel pScope;
    private javax.swing.JScrollPane spField;
    private javax.swing.JScrollPane spFilter;
    private javax.swing.JScrollPane spFilterParameter;
    private javax.swing.JScrollPane spJoin;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JScrollPane spVisualQueryList;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}
