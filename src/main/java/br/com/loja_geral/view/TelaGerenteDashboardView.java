package br.com.loja_geral.view;

import br.com.loja_geral.dto.ProdutoEstoqueDTO;
import br.com.loja_geral.model.Pedido;
import br.com.loja_geral.model.Produto;
import br.com.loja_geral.model.Usuario;
import br.com.loja_geral.service.ServiceProduto;
import br.com.loja_geral.util.SessaoUsuario;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import br.com.loja_geral.controller.ProdutoController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TelaGerenteDashboardView implements Tela {

    ProdutoController produtoController = new ProdutoController(new ServiceProduto());

    private final BorderPane root;
    private Label lblNomeGerente;

    public TelaGerenteDashboardView(Navegador navegador) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #F4F6F8;");

        // Topo: Header Administrativo
        root.setTop(criarHeaderAdmin(navegador));

        // Centro: Painel com Abas de Gerenciamento
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-tab-min-width: 140px; -fx-tab-min-height: 35px;");

        Tab tabDashboard = new Tab("📊 Visão Geral", criarAbaVisaoGeral());
        Tab tabPedidos = new Tab("📦 Gestão de Pedidos", criarAbaPedidos());
        Tab tabProdutos = new Tab("🏷️ CRUD Produtos", criarAbaProdutos());
        Tab tabUsuarios = new Tab("👥 Usuários", criarAbaUsuarios());

        tabPane.getTabs().addAll(tabDashboard, tabPedidos, tabProdutos, tabUsuarios);

        VBox containerCentral = new VBox(tabPane);
        containerCentral.setPadding(new Insets(15));
        root.setCenter(containerCentral);
    }

    // ==========================================
    // 1. HEADER ADMINISTRATIVO
    // ==========================================
    private HBox criarHeaderAdmin(Navegador navegador) {
        HBox header = new HBox(15);
        header.setPadding(new Insets(12, 24, 12, 24));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #1E293B;");

        Label lblLogo = new Label("BrasilVendas - Painel Administrativo");
        lblLogo.setStyle("-fx-text-fill: #38BDF8; -fx-font-size: 20px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        lblNomeGerente = new Label("Gerente: Administrador");
        lblNomeGerente.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Button btnSair = new Button("Sair da Sessão");
        btnSair.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4px;");
        btnSair.setOnAction(e -> {
            SessaoUsuario.encerrarSessao();
            navegador.irPara(TelaLoginView.class, () -> new TelaLoginView(navegador));
        });

        header.getChildren().addAll(lblLogo, spacer, lblNomeGerente, btnSair);
        return header;
    }

    // ==========================================
    // 2. ABA: VISÃO GERAL (CARDS KPI)
    // ==========================================
    private VBox criarAbaVisaoGeral() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label lblTitulo = new Label("Resumo do Sistema");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox cardsBox = new HBox(15);
        cardsBox.getChildren().addAll(
                criarCardKpi("Faturamento Total", "R$ 45.890,00", "#10B981"),
                criarCardKpi("Total de Pedidos", "128", "#3B82F6"),
                criarCardKpi("Produtos Cadastrados", "64", "#F59E0B"),
                criarCardKpi("Clientes Ativos", "312", "#8B5CF6")
        );

        VBox boxAlertas = new VBox(10);
        boxAlertas.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8;");
        Label lblAlertas = new Label("⚠️ Alertas de Estoque Baixo");
        lblAlertas.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #DC2626;");

        ListView<String> listaAlertas = new ListView<>();
        listaAlertas.getItems().addAll(
                "Notebook Dell Inspiron - Apenas 2 unidades restantes",
                "Smartphone Samsung Galaxy A05s - Estoque Esgotado",
                "Teclado Mecânico RGB - Apenas 1 unidade restante"
        );
        listaAlertas.setPrefHeight(120);

        boxAlertas.getChildren().addAll(lblAlertas, listaAlertas);

        layout.getChildren().addAll(lblTitulo, cardsBox, boxAlertas);
        return layout;
    }

    private VBox criarCardKpi(String titulo, String valor, String corHex) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: " + corHex + "; -fx-border-width: 0 0 0 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        Label lblTit = new Label(titulo);
        lblTit.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");

        Label lblVal = new Label(valor);
        lblVal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0F172A;");

        card.getChildren().addAll(lblTit, lblVal);
        return card;
    }

    // ==========================================
    // 3. ABA: GESTÃO DE PEDIDOS (LinkedHashMap<UUID, Pedido>)
    // ==========================================
    private VBox criarAbaPedidos() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        HBox boxFiltro = new HBox(10);
        boxFiltro.setAlignment(Pos.CENTER_LEFT);

        TextField txtFiltroUsuarioId = new TextField();
        txtFiltroUsuarioId.setPromptText("Filtrar por ID do Usuário...");

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnLimparFiltro = new Button("Limpar");

        ComboBox<String> comboStatusGlobal = new ComboBox<>();
        comboStatusGlobal.getItems().addAll("Todos os Status", "PENDENTE", "EM_SEPARACAO", "ENVIADO", "ENTREGUE", "CANCELADO");
        comboStatusGlobal.setValue("Todos os Status");

        boxFiltro.getChildren().addAll(new Label("Filtrar por Usuário ID:"), txtFiltroUsuarioId, btnFiltrar, btnLimparFiltro, new Separator(Orientation.VERTICAL), comboStatusGlobal);

        // Tabela configurada para receber entradas do tipo Map.Entry<UUID, Pedido>
        TableView<Map.Entry<UUID, Pedido>> tabelaPedidos = new TableView<>();

        // Coluna UUID (Chave do Mapa)
        TableColumn<Map.Entry<UUID, Pedido>, UUID> colUuid = new TableColumn<>("UUID do Pedido");
        colUuid.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));

        // Coluna ID do Cliente (do objeto Pedido)
        TableColumn<Map.Entry<UUID, Pedido>, String> colUsuarioId = new TableColumn<>("ID Cliente");
        colUsuarioId.setCellValueFactory(cellData -> {
            Pedido p = cellData.getValue().getValue();
            return new SimpleStringProperty(p != null && p.getIdCliente() != null ? String.valueOf(p.getIdCliente()) : "");
        });

        // Coluna Data (do objeto Pedido)
        TableColumn<Map.Entry<UUID, Pedido>, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData -> {
            Pedido p = cellData.getValue().getValue();
            return new SimpleStringProperty(p != null && p.getMomentoDoPedido() != null ? p.getMomentoDoPedido().toString() : "");
        });

        // Coluna Valor Total (do objeto Pedido)
        TableColumn<Map.Entry<UUID, Pedido>, String> colValor = new TableColumn<>("Valor Total");
        colValor.setCellValueFactory(cellData -> {
            Pedido p = cellData.getValue().getValue();
            return new SimpleStringProperty(p != null && p.getTotal() != null ? p.getTotal().toString() : "0.00");
        });

        // Coluna Status (do objeto Pedido)
        TableColumn<Map.Entry<UUID, Pedido>, String> colStatus = new TableColumn<>("Status de Entrega");
        colStatus.setCellValueFactory(cellData -> {
            Pedido p = cellData.getValue().getValue();
            return new SimpleStringProperty(p != null && p.getStatusPedido() != null ? p.getStatusPedido().toString() : "");
        });

        tabelaPedidos.getColumns().addAll(colUuid, colUsuarioId, colData, colValor, colStatus);
        tabelaPedidos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Exemplo de LinkedHashMap<UUID, Pedido>
        Map<UUID, Pedido> mapaPedidos = new LinkedHashMap<>();

        // Converter o LinkedHashMap.entrySet() em uma ObservableList
        ObservableList<Map.Entry<UUID, Pedido>> listaPedidos = FXCollections.observableArrayList(mapaPedidos.entrySet());
        tabelaPedidos.setItems(listaPedidos);

        // Painel Inferior de Alteração de Status
        HBox boxAtualizarStatus = new HBox(10);
        boxAtualizarStatus.setAlignment(Pos.CENTER_LEFT);
        boxAtualizarStatus.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8;");

        ComboBox<String> comboNovoStatus = new ComboBox<>();
        comboNovoStatus.getItems().addAll("PENDENTE", "EM_SEPARACAO", "ENVIADO", "ENTREGUE", "CANCELADO");
        comboNovoStatus.setPromptText("Selecione o Novo Status");

        Button btnAtualizarStatus = new Button("Atualizar Status do Pedido Selecionado");
        btnAtualizarStatus.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        btnAtualizarStatus.setOnAction(e -> {
            Map.Entry<UUID, Pedido> entrySelecionada = tabelaPedidos.getSelectionModel().getSelectedItem();
            if (entrySelecionada != null && comboNovoStatus.getValue() != null) {
                Pedido pedidoSelecionado = entrySelecionada.getValue();
                if (pedidoSelecionado != null) {
                    tabelaPedidos.refresh();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Status do Pedido UUID " + entrySelecionada.getKey() + " atualizado.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um pedido na tabela e escolha o novo status.");
            }
        });

        boxAtualizarStatus.getChildren().addAll(new Label("Alterar Status:"), comboNovoStatus, btnAtualizarStatus);

        layout.getChildren().addAll(boxFiltro, tabelaPedidos, boxAtualizarStatus);
        return layout;
    }

    // ==========================================
    // 4. ABA: CRUD DE PRODUTOS (COM PRODUTOESTOQUEDTO)
    // ==========================================
    private VBox criarAbaProdutos() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        HBox barraAcoes = new HBox(10);

        Button btnNovo = new Button("➕ Novo Produto");
        btnNovo.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        Button btnEditar = new Button("✏️ Editar");
        btnEditar.setStyle("-fx-background-color: #F59E0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        Button btnExcluir = new Button("🗑️ Excluir");
        btnExcluir.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        TextField txtBuscaProduto = new TextField();
        txtBuscaProduto.setPromptText("Buscar produto...");
        HBox.setHgrow(txtBuscaProduto, Priority.ALWAYS);

        barraAcoes.getChildren().addAll(btnNovo, btnEditar, btnExcluir, txtBuscaProduto);

        // TableView alinhada com ProdutoEstoqueDTO
        TableView<ProdutoEstoqueDTO> tabelaProdutos = new TableView<>();

        // Definição das Colunas
        TableColumn<ProdutoEstoqueDTO, Object> colProdId = new TableColumn<>("ID");
        colProdId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<ProdutoEstoqueDTO, String> colProdNome = new TableColumn<>("Nome");
        colProdNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        TableColumn<ProdutoEstoqueDTO, String> colProdPreco = new TableColumn<>("Preço (R$)");
        colProdPreco.setCellValueFactory(cellData -> {
            BigDecimal preco = cellData.getValue().getPreco();
            return new SimpleStringProperty(preco != null ? String.format("%.2f", preco.doubleValue()) : "0.00");
        });

        TableColumn<ProdutoEstoqueDTO, Object> colProdEstoque = new TableColumn<>("Estoque");
        colProdEstoque.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidadeEstoque()));

        TableColumn<ProdutoEstoqueDTO, String> colProdDescricao = new TableColumn<>("Descrição");
        colProdDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));

        TableColumn<ProdutoEstoqueDTO, String> colProdImagem = new TableColumn<>("Imagem");
        colProdImagem.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPATH_FILE()));

        colProdImagem.setCellFactory(col -> new TableCell<ProdutoEstoqueDTO, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.trim().isEmpty()) {
                    setGraphic(null);
                    setText(null);
                } else {
                    try {
                        String caminhoImagem = item;
                        Image img;

                        if (caminhoImagem.startsWith("http://") || caminhoImagem.startsWith("https://")) {
                            img = new Image(caminhoImagem, 40, 40, true, true, true);
                        } else {
                            java.io.File file = new java.io.File(caminhoImagem);
                            if (file.exists()) {
                                img = new Image(file.toURI().toString(), 40, 40, true, true, true);
                            } else {
                                setGraphic(null);
                                setText("N/A");
                                return;
                            }
                        }

                        imageView.setImage(img);
                        imageView.setFitWidth(40);
                        imageView.setFitHeight(40);
                        imageView.setPreserveRatio(true);

                        setGraphic(imageView);
                        setText(null);
                    } catch (Exception e) {
                        setGraphic(null);
                        setText("Erro");
                    }
                }
            }
        });

        tabelaProdutos.getColumns().addAll(colProdId, colProdNome, colProdPreco, colProdEstoque, colProdDescricao, colProdImagem);
        tabelaProdutos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Dados Iniciais vindos da busca do Controller/DAO
        ObservableList<ProdutoEstoqueDTO> listaProdutos = FXCollections.observableArrayList();

        try {
            List<? extends Produto> produtosBuscados = produtoController.buscarProdutos();
            for (Produto p : produtosBuscados) {
                if (p instanceof ProdutoEstoqueDTO) {
                    listaProdutos.add((ProdutoEstoqueDTO) p);
                } else {
                    listaProdutos.add(new ProdutoEstoqueDTO(p, 0,p.getId()));
                }
            }
        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao carregar lista de produtos.");
        }

        // Configuração do Filtro de Busca Dinâmica
        FilteredList<ProdutoEstoqueDTO> dadosFiltrados = new FilteredList<>(listaProdutos, p -> true);
        txtBuscaProduto.textProperty().addListener((observable, oldValue, newValue) -> {
            dadosFiltrados.setPredicate(prod -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String termo = newValue.toLowerCase();
                String nome = prod.getNome() != null ? prod.getNome().toLowerCase() : "";
                String categoria = prod.getCategoria() != null ? prod.getCategoria().getCategoria().toUpperCase() : "";
                return nome.contains(termo) || categoria.contains(termo);
            });
        });

        tabelaProdutos.setItems(dadosFiltrados);

        // Eventos dos Botões corrigidos para ProdutoEstoqueDTO
        btnNovo.setOnAction(e -> abrirFormularioProduto(null, listaProdutos, tabelaProdutos));

        btnEditar.setOnAction(e -> {
            ProdutoEstoqueDTO selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                abrirFormularioProduto(selecionado, listaProdutos, tabelaProdutos);
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Seleção", "Selecione um produto para editar.");
            }
        });

        btnExcluir.setOnAction(e -> {
            ProdutoEstoqueDTO selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                listaProdutos.remove(selecionado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Removido", "Produto removido com sucesso.");
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Seleção", "Selecione um produto para excluir.");
            }
        });

        layout.getChildren().addAll(barraAcoes, tabelaProdutos);
        return layout;
    }

    private void abrirFormularioProduto(ProdutoEstoqueDTO produto, ObservableList<ProdutoEstoqueDTO> listaProdutos, TableView<ProdutoEstoqueDTO> tabela) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(produto == null ? "Cadastrar Novo Produto" : "Editar Produto");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Campos de Texto
        TextField txtNome = new TextField(produto != null && produto.getNome() != null ? produto.getNome() : "");
        TextField txtPreco = new TextField(produto != null && produto.getPreco() != null ? produto.getPreco().toString() : "");
        TextField txtEstoque = new TextField(produto != null ? String.valueOf(produto.getQuantidadeEstoque()) : "");

        TextArea txtDescricao = new TextArea(produto != null && produto.getDescricao() != null ? produto.getDescricao() : "");
        txtDescricao.setPrefRowCount(3);
        txtDescricao.setWrapText(true);

        // Campo de Imagem + Botão de Seleção
        TextField txtImagemUrl = new TextField(produto != null && produto.getPATH_FILE() != null ? produto.getPATH_FILE() : "");
        txtImagemUrl.setPromptText("Caminho ou URL da imagem...");

        Button btnSelecionarImagem = new Button("📁 Selecionar...");
        btnSelecionarImagem.setOnAction(e -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Selecionar Imagem do Produto");
            fileChooser.getExtensionFilters().addAll(
                    new javafx.stage.FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.webp")
            );
            java.io.File arquivoSelecionado = fileChooser.showOpenDialog(dialog.getDialogPane().getScene().getWindow());
            if (arquivoSelecionado != null) {
                txtImagemUrl.setText(arquivoSelecionado.getAbsolutePath());
            }
        });

        HBox boxImagem = new HBox(8, txtImagemUrl, btnSelecionarImagem);
        HBox.setHgrow(txtImagemUrl, Priority.ALWAYS);

        // Montagem do Layout do Formulário
        grid.add(new Label("Nome:"), 0, 0);
        grid.add(txtNome, 1, 0);

        grid.add(new Label("Preço (R$):"), 0, 2);
        grid.add(txtPreco, 1, 2);

        grid.add(new Label("Quantidade/Estoque:"), 0, 3);
        grid.add(txtEstoque, 1, 3);

        grid.add(new Label("Descrição:"), 0, 4);
        grid.add(txtDescricao, 1, 4);

        grid.add(new Label("Imagem:"), 0, 5);
        grid.add(boxImagem, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                try {
                    String nome = txtNome.getText();
                    BigDecimal preco = new BigDecimal(txtPreco.getText());
                    int est = Integer.parseInt(txtEstoque.getText());
                    String descricao = txtDescricao.getText();
                    String imagemUrl = txtImagemUrl.getText();

                    if (produto == null) {
                        // Salva no BD através do controller
                        produtoController.salvarProduto(nome, descricao, imagemUrl, preco, est);

                        // Cria o modelo base e engloba no DTO
                        Produto p = new Produto(nome, descricao, imagemUrl, preco);
                        p.setId((long) (listaProdutos.size() + 1));

                        ProdutoEstoqueDTO novoDto = new ProdutoEstoqueDTO(p, est,p.getId());
                        listaProdutos.add(novoDto);

                        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto cadastrado com sucesso!");
                    } else {
                        produto.setNome(nome);
                        produto.setPreco(preco);
                        produto.setDescricao(descricao);
                        produto.setPATH_FILE(imagemUrl);

                        tabela.refresh();
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto atualizado com sucesso!");
                    }
                } catch (NumberFormatException ex) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Preço e estoque devem conter valores numéricos válidos.");
                }
            }
        });
    }

    // ==========================================
    // 5. ABA: GESTÃO DE USUÁRIOS
    // ==========================================
    private VBox criarAbaUsuarios() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        TextField txtBuscaUsuario = new TextField();
        txtBuscaUsuario.setPromptText("Buscar usuário por Nome, CPF ou E-mail...");

        TableView<Usuario> tabelaUsuarios = new TableView<>();

        TableColumn<Usuario, String> colUsrNome = new TableColumn<>("Nome Completo");
        colUsrNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        TableColumn<Usuario, String> colUsrEmail = new TableColumn<>("E-mail");
        colUsrEmail.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getEmail() != null ? cellData.getValue().getEmail().getEndereco() : ""
        ));

        TableColumn<Usuario, String> colUsrCpf = new TableColumn<>("CPF");
        colUsrCpf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCpf()));

        tabelaUsuarios.getColumns().addAll(colUsrNome, colUsrEmail, colUsrCpf);
        tabelaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        layout.getChildren().addAll(txtBuscaUsuario, tabelaUsuarios);
        return layout;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void aoExibir() {
        Usuario gerenteLogado = SessaoUsuario.getUsuarioLogado();
        if (gerenteLogado != null && gerenteLogado.getNome() != null) {
            lblNomeGerente.setText("Gerente: " + gerenteLogado.getNome());
        } else {
            lblNomeGerente.setText("Gerente: Administrador");
        }
    }
}