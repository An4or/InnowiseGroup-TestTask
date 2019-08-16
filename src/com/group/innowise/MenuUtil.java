package com.group.innowise;

public class MenuUtil {

    private static int choicePosition(int min, int max) {
        int res = ConsoleHelper.readInt();
        if (res < min || res > max) {
            ConsoleHelper.writeMessage("Invalid input: try again");
            return choicePosition(min, max);
        } else {
            return res;
        }
    }

    private static User getUser() {
        String yn = "";
        User user = new User();
        ConsoleHelper.writeMessage("Enter first name: ");
        String fName = ConsoleHelper.readString();
        user.setFirstName(fName);

        ConsoleHelper.writeMessage("Enter last name: ");
        String lname = ConsoleHelper.readString();
        user.setLastName(lname);


        do {
            ConsoleHelper.writeMessage("Enter role №" + (user.getRoles().size() + 1));
            String role = ConsoleHelper.readString();
            user.setRole(role);
            ConsoleHelper.writeMessage("Add another role?  press y/n ");
            yn = ConsoleHelper.readString();
            if (!"y".equalsIgnoreCase(yn) || user.getRoles().size() == 3) {
                ConsoleHelper.writeMessage("Sorry, limit roles exceeded. Pay for a premium account, and add at least up to 4 roles))");
                break;
            } else if ("n".equalsIgnoreCase(yn)) {
                break;
            }
        } while (true);
        user.fillDefaultRole();

        ConsoleHelper.writeMessage("Enter email: ");
        String email = ConsoleHelper.readString();
        while (!user.setEmail(email)) {
            ConsoleHelper.writeMessage("Invalid input. Please, try again");
            email = ConsoleHelper.readString();
        }

        do {
            System.out.println("Enter phone number №" + (user.getPhones().size() + 1));
            String phone = ConsoleHelper.readString();
            while (!user.setPhone(phone)) {
                ConsoleHelper.writeMessage("Invalid input. Please, try again");
                phone = ConsoleHelper.readString();
            }
            ConsoleHelper.writeMessage("Add another phone number?  press y/n ");
            yn = ConsoleHelper.readString();
            if (!"y".equalsIgnoreCase(yn) || user.getPhones().size() == 3) {
                ConsoleHelper.writeMessage("Sorry, limit phone number exceeded. Pay for a premium account, and add at least up to 4 roles))");
                break;
            } else if ("n".equalsIgnoreCase(yn)) {
                break;
            }
        } while (true);

        user.fillDefaultPhone();

        return user;
    }

    public static void userContactMenu() {
        UserContact userContact = new UserContact();
        boolean exit = false;
        while (!exit) {
            ConsoleHelper.writeMessage("Total user(s): " + userContact.numOfUsers());
            ConsoleHelper.writeMessage("1. Add new user");
            ConsoleHelper.writeMessage("2. Search user(s)");
            ConsoleHelper.writeMessage("3. Print all");
            ConsoleHelper.writeMessage("4. Exit");
            int choice = choicePosition(1, 4);

            if (choice == 1) {
                User user = getUser();
                userContact.addUser(user);

            } else if (choice == 2) {
                ConsoleHelper.writeMessage("Enter a name, email or phone number to search user(s):");
                String user = ConsoleHelper.readString();
                userContact.printAll(userContact.searchUsers(user));
                int choice2 = 0;
                do {
                    if (choice2 == 1) {
                        ConsoleHelper.writeMessage("Enter a name, email or phone number to search user(s):");
                        user = ConsoleHelper.readString();
                        userContact.printAll(userContact.searchUsers(user));
                    } else if (choice2 == 2) {
                        ConsoleHelper.writeMessage("Confirm deletion by pressing y/n");
                        String yn = ConsoleHelper.readString();
                        if ("y".equalsIgnoreCase(yn)) {
                            userContact.deleteUser(userContact.searchUsers(user));
                        }
                    } else if (choice2 == 3) {
                        if (userContact.searchUsers(user).size() > 1) {
                            ConsoleHelper.writeMessage("Select a user to edit");
                        } else {
                            int choice3 = 0;
                            String edit;
                            do {
                                User userEdit = userContact.searchUsers(user).get(0);
                                if (choice3 == 1) {
                                    ConsoleHelper.writeMessage("Enter new first name");
                                    edit = ConsoleHelper.readString();
                                    userEdit.setFirstName(edit);
                                } else if (choice3 == 2) {
                                    ConsoleHelper.writeMessage("Enter new last name");
                                    edit = ConsoleHelper.readString();
                                    userEdit.setLastName(edit);
                                } else if (choice3 == 3) {
                                    ConsoleHelper.writeMessage("Enter new role1");
                                    edit = ConsoleHelper.readString();
                                    userEdit.getRoles().set(0, userEdit.getRole(edit));
                                } else if (choice3 == 4) {
                                    ConsoleHelper.writeMessage("Enter new role2");
                                    edit = ConsoleHelper.readString();
                                    userEdit.getRoles().set(1, userEdit.getRole(edit));
                                } else if (choice3 == 5) {
                                    ConsoleHelper.writeMessage("Enter new role3");
                                    edit = ConsoleHelper.readString();
                                    userEdit.getRoles().set(2, userEdit.getRole(edit));
                                } else if (choice3 == 6) {
                                    ConsoleHelper.writeMessage("Enter new email");
                                    edit = ConsoleHelper.readString();
                                    while (!userEdit.setEmail(edit)) {
                                        ConsoleHelper.writeMessage("Invalid input. Please, try again");
                                        edit = ConsoleHelper.readString();
                                    }
                                } else if (choice3 == 7) {
                                    ConsoleHelper.writeMessage("Enter new phone1");
                                    edit = ConsoleHelper.readString();
                                    while (!new User().setPhone(edit)) {
                                        ConsoleHelper.writeMessage("Invalid input. Please, try again");
                                        edit = ConsoleHelper.readString();
                                    }
                                    userEdit.getPhones().set(0, userEdit.getPhone(edit));
                                }else if (choice3 == 8) {
                                    ConsoleHelper.writeMessage("Enter new phone2");
                                    edit = ConsoleHelper.readString();
                                    while (!new User().setPhone(edit)) {
                                        ConsoleHelper.writeMessage("Invalid input. Please, try again");
                                        edit = ConsoleHelper.readString();
                                    }
                                    userEdit.getPhones().set(1, userEdit.getPhone(edit));
                                }else if (choice3 == 9) {
                                    ConsoleHelper.writeMessage("Enter new phone3");
                                    edit = ConsoleHelper.readString();
                                    while (!new User().setPhone(edit)) {
                                        ConsoleHelper.writeMessage("Invalid input. Please, try again");
                                        edit = ConsoleHelper.readString();
                                    }
                                    userEdit.getPhones().set(2, userEdit.getPhone(edit));
                                }
                                ConsoleHelper.writeMessage("1. Edit first name");
                                ConsoleHelper.writeMessage("2. Edit last name");
                                ConsoleHelper.writeMessage("3. Edit role1");
                                ConsoleHelper.writeMessage("4. Edit role2");
                                ConsoleHelper.writeMessage("5. Edit role3");
                                ConsoleHelper.writeMessage("6. Edit email");
                                ConsoleHelper.writeMessage("7. Edit phone1");
                                ConsoleHelper.writeMessage("8. Edit phone2");
                                ConsoleHelper.writeMessage("9. Edit phone3");
                                ConsoleHelper.writeMessage("10. Apply changes and exit to the main menu");
                                ConsoleHelper.writeMessage("0. Return to main menu");
                                choice3 = choicePosition(0, 10);
                                if (choice3 == 10){
                                    userContact.deleteUser(userContact.searchUsers(user));
                                    userContact.addUser(userEdit);
                                    break;
                                }
                            } while (choice3 != 0);
                        }

                    }
                    ConsoleHelper.writeMessage("1. New search");
                    ConsoleHelper.writeMessage("2. Deleted user(s)");
                    ConsoleHelper.writeMessage("3. Edit user data");
                    ConsoleHelper.writeMessage("4. Return to main menu");
                    choice2 = choicePosition(1, 4);

                } while (choice2 != 4);


            } else if (choice == 3) {
                userContact.printAll();
            } else {
                exit = true;
            }
        }

//        userContact.saveToFile();
        userContact.saveToXML();
    }

}
