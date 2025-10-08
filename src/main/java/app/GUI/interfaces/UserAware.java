
package app.GUI.interfaces;

import app.DTO.Account;

public interface UserAware {
    void onUserChanged(Account userAccount);
}