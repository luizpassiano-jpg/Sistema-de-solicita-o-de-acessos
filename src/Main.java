import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        RoleDAO roleDAO = new RoleDAO();
        AccessRequestDAO accessRequestDAO = new AccessRequestDAO();

        System.out.println("=== SISTEMA DE REQUISIÇÃO DE ACESSO ===");
        System.out.println("Login obrigatório");

        System.out.print("Usuário: ");
        String loginUser = scanner.nextLine();

        System.out.print("Senha: ");
        String loginPassword = scanner.nextLine();

        boolean autenticado = userDAO.login(loginUser, loginPassword);
        boolean isAdmin = userDAO.isAdmin(loginUser);

        if (!autenticado) {
            System.out.println("Login inválido. Encerrando sistema.");
            scanner.close();
            return;
        }

        System.out.println("Login realizado com sucesso!");

        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Criar usuário");
            System.out.println("2 - Solicitar permissão");
            System.out.println("3 - Aprovar permissão");
            System.out.println("4 - Excluir usuário");
            System.out.println("5 - Listar usuários");
            System.out.println("6 - Listar roles");
            System.out.println("7 - Listar solicitações");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do novo usuário: ");
                    String newUsername = scanner.nextLine();

                    System.out.print("Digite a senha do novo usuário: ");
                    String newPassword = scanner.nextLine();

                    userDAO.createUser(newUsername, newPassword);
                    break;

                case 2:
                    userDAO.listUsers();
                    roleDAO.listRoles();

                    System.out.print("Digite o ID do usuário: ");
                    int userId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Digite o ID da role: ");
                    int roleId = Integer.parseInt(scanner.nextLine());

                    accessRequestDAO.requestAccess(userId, roleId);
                    break;

                case 3:
                    if (!isAdmin) {
                        System.out.println("Apenas administradores podem aprovar acessos!");
                        break;
                    }

                    accessRequestDAO.listRequests();

                    System.out.print("Digite o ID da solicitação: ");
                    int requestId = Integer.parseInt(scanner.nextLine());

                    accessRequestDAO.approveRequest(requestId);
                    break;

                case 4:
                    userDAO.listUsers();

                    System.out.print("Digite o ID do usuário que deseja excluir: ");
                    int deleteUserId = Integer.parseInt(scanner.nextLine());

                    userDAO.deleteUser(deleteUserId);
                    break;

                case 5:
                    userDAO.listUsers();
                    break;

                case 6:
                    roleDAO.listRoles();
                    break;

                case 7:
                    accessRequestDAO.listRequests();
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}