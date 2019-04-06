package by.iba.web.command;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public enum CommandType {
  AUTHENTICATION {
    {
      this.command = new LoginCommand();
    }
  },
  ADD {
    {
      this.command = new AddPersonCommand();
    }
  },
  SIGN_UP {
    {
      this.command = new RegisterCommand();
    }
  },
  LOGOUT {
    {
      this.command = new LogoutCommand();
    }
  },

  REGISTER {
    {
      this.command = new GoToRegisterCommand();
    }
  },

  LOGIN {
    {
      this.command = new GoToLoginCommand();
    }
  },

  PERSONS {
    {
      this.command = new ShowPersonsCommand();
    }
  },
  GO_TO_PERSON {
    {
      this.command = new GoToAddPersonCommand();
    }
  };

  public ActionCommand command;

  public ActionCommand getCurrentCommand() {
    return this.command;
  }

  public static <T extends Enum<T>> T getEnumFromString(Class<T> clazz, String value) {

    requireNonNull(clazz);
    requireNonNull(value);

    String commandName =
        Arrays.stream(clazz.getEnumConstants())
            .filter(enumConstant -> value.contains(enumConstant.toString()))
            .findFirst()
            .map(Enum::name)
            .orElse(null);
    requireNonNull(commandName, "Value '" + value + " not added to list of available command.");
    return Enum.valueOf(clazz, commandName);
  }
}
