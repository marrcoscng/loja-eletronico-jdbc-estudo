package br.com.loja_geral.view;

import br.com.loja_geral.model.Cliente;
import br.com.loja_geral.model.Usuario;
import br.com.loja_geral.util.SessaoUsuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TelaClienteDashboardView implements Tela {

    private final BorderPane root;
    private final StackPane areaConteudo;
    private Label lblQtdCarrinho;
    private TextField txtBusca;
    private Button btnPerfil; // Transformado em atributo para permitir atualização dinâmica

    public TelaClienteDashboardView(Navegador navegador) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #F4F6F8;");

        // Sub-barra de Categorias e Header no Topo
        VBox topoCompleto = new VBox(criarHeader(navegador), criarBarraCategorias());
        root.setTop(topoCompleto);

        // Área Central Dinâmica
        areaConteudo = new StackPane();
        areaConteudo.setPadding(new Insets(20));
        root.setCenter(areaConteudo);

        // Exibe a vitrine por padrão
        mostrarSecaoVitrine();
    }

    // ==========================================
    // 1. BARRA SUPERIOR (HEADER)
    // ==========================================
    private HBox criarHeader(Navegador navegador) {
        HBox header = new HBox(15);
        header.setPadding(new Insets(12, 24, 12, 24));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #0F1111;");

        // Logo
        Label lblLogo = new Label("BrasilVendas");
        lblLogo.setStyle("-fx-text-fill: #FF9900; -fx-font-size: 22px; -fx-font-weight: bold;");

        // Barra de Pesquisa
        txtBusca = new TextField();
        txtBusca.setPromptText("Buscar produtos, marcas e muito mais...");
        HBox.setHgrow(txtBusca, Priority.ALWAYS);
        txtBusca.setStyle("-fx-padding: 8px; -fx-background-radius: 4px 0 0 4px;");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #FF9900; -fx-text-fill: #0F1111; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-cursor: hand; -fx-background-radius: 0 4px 4px 0;");

        HBox boxBusca = new HBox(txtBusca, btnBuscar);
        boxBusca.setAlignment(Pos.CENTER);
        HBox.setHgrow(boxBusca, Priority.ALWAYS);

        // Botão Perfil (o texto é gerado/atualizado dinamicamente em atualizarHeader)
        btnPerfil = new Button("Olá, Cliente");
        btnPerfil.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnPerfil.setOnAction(e -> mostrarSecaoPerfil());

        Button btnPedidos = new Button("Meus Pedidos");
        btnPedidos.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand;");
        btnPedidos.setOnAction(e -> mostrarSecaoPedidos());

        // Botão Carrinho
        lblQtdCarrinho = new Label("0");
        lblQtdCarrinho.setStyle("-fx-background-color: #FF9900; -fx-text-fill: #0F1111; -fx-font-weight: bold; -fx-padding: 2px 6px; -fx-background-radius: 10px;");

        Button btnCarrinho = new Button("Carrinho");
        btnCarrinho.setStyle("-fx-background-color: #232F3E; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        HBox boxCarrinho = new HBox(5, btnCarrinho, lblQtdCarrinho);
        boxCarrinho.setAlignment(Pos.CENTER);
        boxCarrinho.setOnMouseClicked(e -> mostrarSecaoCarrinho());

        // Botão Sair (Encerra a Sessão)
        Button btnSair = new Button("Sair");
        btnSair.setStyle("-fx-background-color: transparent; -fx-text-fill: #E53E3E; -fx-cursor: hand;");

        btnSair.setOnAction(event -> {
            SessaoUsuario.encerrarSessao();
            navegador.irPara(TelaLoginView.class, () -> new TelaLoginView(navegador));
        });

        header.getChildren().addAll(lblLogo, boxBusca, btnPerfil, btnPedidos, boxCarrinho, btnSair);
        return header;
    }

    private HBox criarBarraCategorias() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(8, 24, 8, 24));
        barra.setStyle("-fx-background-color: #232F3E;");

        String[] categorias = {"Todas as Categorias", "Eletrônicos", "Informática", "Smartphones", "Eletrodomésticos", "Promoções do Dia"};

        for (String cat : categorias) {
            Hyperlink link = new Hyperlink(cat);
            link.setStyle("-fx-text-fill: white; -fx-underline: false; -fx-font-weight: bold;");
            link.setOnAction(e -> mostrarSecaoVitrine());
            barra.getChildren().add(link);
        }

        return barra;
    }

    // ==========================================
    // 2. SEÇÃO: VITRINE DE PRODUTOS
    // ==========================================
    private void mostrarSecaoVitrine() {
        BorderPane layoutVitrine = new BorderPane();

        VBox painelFiltros = new VBox(15);
        painelFiltros.setPadding(new Insets(15));
        painelFiltros.setPrefWidth(220);
        painelFiltros.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        Label lblTituloFiltro = new Label("Filtrar Resultados");
        lblTituloFiltro.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Slider sliderPreco = new Slider(0, 5000, 2500);
        sliderPreco.setShowTickLabels(true);

        painelFiltros.getChildren().addAll(
                lblTituloFiltro, new Separator(),
                new Label("Faixa de Preço:"), sliderPreco,
                new Label("Marcas:"), new CheckBox("Samsung"), new CheckBox("Apple"), new CheckBox("Dell")
        );
        layoutVitrine.setLeft(painelFiltros);

        ScrollPane scrollProdutos = new ScrollPane();
        scrollProdutos.setFitToWidth(true);
        scrollProdutos.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        FlowPane gridProdutos = new FlowPane();
        gridProdutos.setHgap(15);
        gridProdutos.setVgap(15);
        gridProdutos.setPadding(new Insets(0, 0, 0, 15));

        for (int i = 1; i <= 6; i++) {
            gridProdutos.getChildren().add(criarCardProduto("Produto Exemplo " + i, "R$ " + (150 * i) + ",00"));
        }

        scrollProdutos.setContent(gridProdutos);
        layoutVitrine.setCenter(scrollProdutos);

        areaConteudo.getChildren().setAll(layoutVitrine);
    }

    private VBox criarCardProduto(String nome, String preco) {
        VBox card = new VBox(10);
        card.setPrefWidth(200);
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 8, 0, 0, 2);");

        StackPane imgPlaceholder = new StackPane(new Label("[ Imagem ]"));
        imgPlaceholder.setPrefSize(160, 140);
        imgPlaceholder.setStyle("-fx-background-color: #EDF2F7; -fx-background-radius: 4px;");

        Label lblNome = new Label(nome);
        lblNome.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label lblPreco = new Label(preco);
        lblPreco.setStyle("-fx-text-fill: #B12704; -fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnAdicionar = new Button("Adicionar ao Carrinho");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setStyle("-fx-background-color: #FFD814; -fx-text-fill: #0F1111; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20px;");

        card.getChildren().addAll(imgPlaceholder, lblNome, lblPreco, btnAdicionar);
        return card;
    }

    // ==========================================
    // 3. SEÇÃO: PERFIL COM DADOS DA SESSÃO
    // ==========================================
    private void mostrarSecaoPerfil() {
        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        VBox containerPerfil = new VBox(15);
        containerPerfil.setMaxWidth(600);
        containerPerfil.setPadding(new Insets(25));
        containerPerfil.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        Label lblTitulo = new Label("Dados Pessoais e Endereço");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Preenchimento dos campos com dados reais do cliente logado
        TextField txtNome = new TextField(usuario != null && usuario.getNome() != null ? usuario.getNome() : "");
        TextField txtEmail = new TextField(usuario != null && usuario.getEmail() != null ? usuario.getEmail().getEndereco() : "");
        TextField txtCpf = new TextField(usuario != null && usuario.getCpf() != null ? usuario.getCpf() : "");
        TextField txtEndereco = new TextField(
                (usuario instanceof Cliente cliente && cliente.getEndereco() != null)
                        ? cliente.getEndereco().toString()
                        : ""
        );

        containerPerfil.getChildren().addAll(
                lblTitulo,
                new Label("Nome Completo"), txtNome,
                new Label("E-mail"), txtEmail,
                new Label("CPF"), txtCpf,
                new Label("Endereço de Entrega"), txtEndereco
        );

        areaConteudo.getChildren().setAll(containerPerfil);
    }

    private void mostrarSecaoCarrinho() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");
        container.getChildren().addAll(
                new Label("Seu Carrinho de Compras"),
                new Label("Nenhum item adicionado até o momento.")
        );
        areaConteudo.getChildren().setAll(container);
    }

    private void mostrarSecaoPedidos() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");
        container.getChildren().addAll(
                new Label("Meus Pedidos"),
                new Label("Você ainda não possui pedidos realizados.")
        );
        areaConteudo.getChildren().setAll(container);
    }

    private void atualizarHeader() {
        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        String nomeExibicao = (usuario != null && usuario.getNome() != null && !usuario.getNome().isBlank())
                ? usuario.getNome().split(" ")[0]
                : "Cliente";

        if (btnPerfil != null) {
            btnPerfil.setText("Olá, " + nomeExibicao);
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void aoExibir() {
        atualizarHeader();
        mostrarSecaoVitrine();
    }
}