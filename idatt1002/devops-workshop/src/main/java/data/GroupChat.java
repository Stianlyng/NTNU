package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the GroupChat object as saved in database
 */
public class GroupChat {
  private int groupChatId;
  private String groupChatName;
  private List<Message> messageList = new ArrayList<>();
  private List<User> userList = new ArrayList<>();

  public GroupChat() {}

  public GroupChat(int groupChatId, String groupChatName) {
    this.groupChatId = groupChatId;
    this.groupChatName = groupChatName;
  }

  /**
   * The method retrieves the group chat ID.
   *
   * @return the group chat ID.
   */
  public int getGroupChatId() {
    return groupChatId;
  }

  /**
   * The method retrieves the group chat name.
   *
   * @return the group chat name.
   */
  public String getGroupChatName() {
    return groupChatName;
  }

  /**
   * The method retrieves a list of messages.
   *
   * @return list of messages.
   */
  public List<Message> getMessageList() {
    return messageList;
  }

  /**
   * The method retrieves a list of users.
   *
   * @return list of users.
   */
  public List<User> getUserList() {
    return userList;
  }

  /**
   * The method sets a new group chat ID.
   *
   * @param groupChatId new group chat ID.
   */
  public void setGroupChatId(int groupChatId) {
    this.groupChatId = groupChatId;
  }

  /**
   * The method sets a new group chat name.
   *
   * @param groupChatName new group chat name.
   */
  public void setGroupChatName(String groupChatName) {
    this.groupChatName = groupChatName;
  }

  /**
   * The method sets a new list of messages.
   *
   * @param messageList new list of messages.
   */
  public void setMessageList(List<Message> messageList) {
    this.messageList = messageList;
  }

  /**
   * The method sets a new list of users.
   *
   * @param userList new list of users.
   */
  public void setUserList(List<User> userList) {
    this.userList = userList;
  }
}
