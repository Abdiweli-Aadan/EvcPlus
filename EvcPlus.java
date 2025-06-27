import java.util.Scanner;

public class EvcPlus {
    // Scanner si user u geliyo xog
    static Scanner input = new Scanner(System.in);

    // PIN default & balance koonto
    static String defaultPIN = "2525";
    static double balance = 1000.0;

    // Log taariikhda macaamilka
    static String[] transactionLog = new String[500];
    static int logIndex = 0;

    // Tirada isku day khaldan
    static int failedAttempts = 0;

    // Salaam bank balance
    static double salaamBalance = 0.0;

    public static void main(String[] args) {
        displayWelcome();         // Soo dhaweyn
        if (handleLogin()) {      // Login xaqiijin
            showMainMenu();       // Haddii uu sax yahay, gal menu
        } else {
            System.out.println("❌ Xisaabtaada waa la xiray kadib 3 jeer oo PIN khaldan ah.");
        }
    }

    // Qeybta 1.1: Soo dhawoow
    public static void displayWelcome() {
        System.out.println("===============================================");
        System.out.println("     Ku Soo Dhawoow Adeegga EVC PLUS           ");
        System.out.println("===============================================");
    }

    // Qeybta 1.2: Login + PIN Hubin
    public static boolean handleLogin() {
        System.out.print("Fadlan geli code-ka adeegga (*770#): ");
        String ussd = input.nextLine().trim();
        if (!ussd.equals("*770#")) {
            System.out.println("❌ Code-ka waa khaldan. Program-ka wuu istaagay.");
            return false;
        }

        while (failedAttempts < 3) {
            System.out.print("Geli PIN-kaaga (4 digits): ");
            String pin = input.nextLine().trim();

            if (pin.equals(defaultPIN)) {
                System.out.println("✅ PIN sax ah. Ku soo dhawoow!");
                return true;
            } else {
                failedAttempts++;
                System.out.println("❌ PIN-ka waa khaldan.");
                if (failedAttempts == 2) {
                    System.out.println("⚠ Hal fursad ayaa kuu haray!");
                }
            }
        }

        return false;
    }

    // Qeybta 2: Muuji menu weyn oo user dooran karo adeegyada
    public static void showMainMenu() {
        boolean continueUsing = true;
        while (continueUsing) {
            System.out.println("\n========= MENU-GA EVC PLUS =========");
            System.out.println("1. Eeg Haraaga (Check Balance)");
            System.out.println("2. Dir Lacag (Send Money)");
            System.out.println("3. La Bax Lacag (Withdraw)");
            System.out.println("4. Iibso Airtime (Naftaada)");
            System.out.println("5. Iibso Airtime (Qof kale)");
            System.out.println("6. Bixi Biilasha");
            System.out.println("7. Salaam Bank");
            System.out.println("8. Voucher Recharge");
            System.out.println("9. Bedel PIN");
            System.out.println("10. Taariikhda Macaamilka");
            System.out.println("11. Ka Bax");

            System.out.print("Fadlan dooro (1-11): ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    checkBalance();
                    break;
                case "2":
                    sendMoney();
                    break;
                case "3":
                    withdrawMoney();
                    break;
                case "4":
                    buyAirtimeSelf();
                    break;
                case "5":
                    buyAirtimeOthers();
                    break;
                case "6":
                    payBills();
                    break;
                case "7":
                    salaamBank();
                    break;
                case "8":
                    voucherRecharge();
                    break;
                case "9":
                    changePIN();
                    break;
                case "10":
                    viewTransactionLog();
                    break;
                case "11":
                    System.out.println("👋 Mahadsanid isticmaalka EVCPlus. Nabad gelyo!");
                    continueUsing = false;
                    break;
                default:
                    System.out.println("❗ Fadlan dooro doorasho sax ah (1-11).");
            }
        }
    }

    // Qeybta 3.1: Eeg Haraaga (Check Balance)
    public static void checkBalance() {
        System.out.printf("💰 Haraagaaga waa: $%.2f\n", balance);
        saveToLog("Eegay Haraaga - $" + balance);
    }

    // Qeybta 3.2: Save to Log (taariikhda macaamilka ku dar)
    public static void saveToLog(String action) {
        if (logIndex < transactionLog.length) {
            transactionLog[logIndex++] = action;
        }
    }

    // Qeybta 4: Dir Lacag (Send Money
    public static void sendMoney() {
        System.out.print("Geli lambarka qofka aad lacagta u dirayso: ");
        String recipientNumber = input.nextLine();

        System.out.print("Geli qadarka lacagta ($): ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Qadarka waa inuu noqdaa tiro sax ah.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Qadarka waa inuu ka weyn yahay 0.");
            return;
        }

        if (amount > balance) {
            System.out.println("Haraagaagu kuma filna lacagtaas.");
            return;
        }

        System.out.print("Ma xaqiijinaysaa inaad u dirto $" + amount + " lambarka " + recipientNumber + "? (haa/maya): ");
        String confirm = input.nextLine().trim();

        if (confirm.equalsIgnoreCase("haa")) {
            balance -= amount;
            System.out.println("✔ Waxaad u dirtay $" + amount + " -> " + recipientNumber);
            saveToLog("Diray $" + amount + " -> " + recipientNumber);
        } else {
            System.out.println("Macaamilka waa la joojiyey.");
        }
    }

    // Qeybta 5: La Bax Lacag (Withdraw Money)
    public static void withdrawMoney() {
        System.out.print("Geli qadarka aad la baxayso ($): ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Qadarka waa inuu noqdaa tiro sax ah.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Qadarka waa inuu ka weyn yahay 0.");
            return;
        }

        if (amount > balance) {
            System.out.println("Ma haysid lacag ku filan haraagaaga.");
            return;
        }

        balance -= amount;
        System.out.println("✔ Waxaad la baxday $" + amount);
        saveToLog("La baxay $" + amount);
    }

    // Qeybta 6.1: Iibso Airtime Naftaada
    public static void buyAirtimeSelf() {
        System.out.print("Geli qadarka Airtime aad rabto inaad iibsatid ($): ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Qadarka waa inuu noqdaa tiro sax ah.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Qadarka waa inuu ka weyn yahay 0.");
            return;
        }

        if (amount > balance) {
            System.out.println("Haraagaagu kuma filna lacagtaas.");
            return;
        }

        balance -= amount;
        System.out.println("✔ Waxaad iibsatay Airtime $" + amount + " naftaada.");
        saveToLog("Iibsaday Airtime $" + amount + " naftaada");
    }

    // Qeybta 6.2: Iibso Airtime qof kale
    public static void buyAirtimeOthers() {
        System.out.print("Geli lambarka qofka aad Airtime u iibsaneyso: ");
        String number = input.nextLine();

        System.out.print("Geli qadarka Airtime ($): ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Qadarka waa inuu noqdaa tiro sax ah.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Qadarka waa inuu ka weyn yahay 0.");
            return;
        }

        if (amount > balance) {
            System.out.println("Haraagaagu kuma filna lacagtaas.");
            return;
        }

        balance -= amount;
        System.out.println("✔ Waxaad u dirtay Airtime $" + amount + " lambarka: " + number);
        saveToLog("U diray Airtime $" + amount + " -> " + number);
    }

    // Qeybta 7: Bixi Biilasha
    public static void payBills() {
        System.out.println("\n-- Noocyada Biilasha --");
        System.out.println("1. Koronto");
        System.out.println("2. Biyo");
        System.out.println("3. Internet");
        System.out.print("Dooro nooca biilka (1-3): ");
        String choice = input.nextLine();

        String billType;
        switch (choice) {
            case "1":
                billType = "Koronto";
                break;
            case "2":
                billType = "Biyo";
                break;
            case "3":
                billType = "Internet";
                break;
            default:
                System.out.println("Doorasho khaldan.");
                return;
        }

        System.out.print("Geli qadarka biilka ($): ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Qadarka waa inuu noqdaa tiro sax ah.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Qadarka waa inuu ka weyn yahay 0.");
            return;
        }

        if (amount > balance) {
            System.out.println("Haraagaagu kuma filna lacagtaas.");
            return;
        }

        balance -= amount;
        System.out.println("✔ Waxaad bixisay $" + amount + " biilka: " + billType);
        saveToLog("Bixisay $" + amount + " biilka: " + billType);
    }

    // Qeybta 8: Salaam Bank - Maareynta koontada Salaam Bank
    public static void salaamBank() {
        System.out.println("\n-- Salaam Bank --");
        System.out.println("1. Ka dhig lacag Salaam Bank");
        System.out.println("2. Ka bax lacag Salaam Bank");
        System.out.println("3. Eeg Haraaga Salaam Bank");
        System.out.print("Dooro (1-3): ");
        String choice = input.nextLine();

        switch (choice) {
            case "1":
                depositSalaam();
                break;
            case "2":
                withdrawSalaam();
                break;
            case "3":
                checkSalaamBalance();
                break;
            default:
                System.out.println("Doorasho khaldan.");
        }
    }

    // 8.1 Ka dhig lacag Salaam Bank
    public static void depositSalaam() {
        System.out.print("Geli qadarka aad ku dhigayso Salaam Bank ($): ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Qadarka waa inuu noqdaa tiro sax ah.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Qadarka waa inuu ka weyn yahay 0.");
            return;
        }

        if (amount > balance) {
            System.out.println("Haraagaagu kuma filna lacagtaas.");
            return;
        }

        balance -= amount;
        salaamBalance += amount;
        System.out.println("✔ Waxaad ku dhigtay Salaam Bank $" + amount);
        saveToLog("Ku dhigay Salaam Bank $" + amount);
    }

    // 8.2 Ka bax lacag Salaam Bank
    public static void withdrawSalaam() {
        System.out.print("Geli qadarka aad ka bixinayso Salaam Bank ($): ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Qadarka waa inuu noqdaa tiro sax ah.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Qadarka waa inuu ka weyn yahay 0.");
            return;
        }

        if (amount > salaamBalance) {
            System.out.println("Haraaga Salaam Bank kuma filna.");
            return;
        }

        salaamBalance -= amount;
        balance += amount;
        System.out.println("✔ Waxaad ka baxday Salaam Bank $" + amount);
        saveToLog("Ka baxay Salaam Bank $" + amount);
    }

    // 8.3 Eeg haraaga Salaam Bank
    public static void checkSalaamBalance() {
        System.out.printf("💰 Haraaga Salaam Bank waa: $%.2f\n", salaamBalance);
        saveToLog("Eegay Haraaga Salaam Bank - $" + salaamBalance);
    }

    // Qeybta 9: Voucher Recharge
    public static void voucherRecharge() {
        System.out.print("Geli code-ka Voucher-ka: ");
        String voucherCode = input.nextLine();

        // Voucher code sax ah - tusaale ahaan, koodhka saxda ah waa "ABC123"
        // Haddii code kale la geliyo, waa khalad
        if (voucherCode.equalsIgnoreCase("ABC123")) {
            double voucherAmount = 50.0; // Lacag ku jirta voucher-ka
            balance += voucherAmount;
            System.out.println("✔ Voucher code sax ah! Lacag $" + voucherAmount + " ayaa lagu daray haraagaaga.");
            saveToLog("Voucher Recharge: +$" + voucherAmount);
        } else {
            System.out.println("❌ Code Voucher waa khaldan.");
        }
    }

    // Qeybta 10.1: Bedel PIN
    public static void changePIN() {
        System.out.print("Geli PIN-kii hore: ");
        String oldPin = input.nextLine();

        if (!oldPin.equals(defaultPIN)) {
            System.out.println("❌ PIN-kii hore waa khaldan.");
            return;
        }

        System.out.print("Geli PIN cusub (4 digits): ");
        String newPin = input.nextLine();

        if (newPin.length() != 4 || !newPin.matches("\\d+")) {
            System.out.println("❌ PIN-ka cusub waa inuu noqdaa 4 lambar oo keliya.");
            return;
        }

        defaultPIN = newPin;
        System.out.println("✔ PIN waa la bedelay si guul leh.");
        saveToLog("Bedelay PIN");
    }

    // Qeybta 10.2: Muuji taariikhda macaamillada
    public static void viewTransactionLog() {
        System.out.println("\n📋 Taariikhda macaamilka:");
        if (logIndex == 0) {
            System.out.println("– Ma jiro wax dhaqdhaqaaq ah wali.");
        } else {
            for (int i = 0; i < logIndex; i++) {
                System.out.println((i + 1) + ". " + transactionLog[i]);
            }
        }
    }
}